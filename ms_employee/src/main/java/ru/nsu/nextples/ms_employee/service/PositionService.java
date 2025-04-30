package ru.nsu.nextples.ms_employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_employee.dto.position.PositionCreateDTO;
import ru.nsu.nextples.ms_employee.dto.position.PositionDTO;
import ru.nsu.nextples.ms_employee.dto.position.PositionUpdateDTO;
import ru.nsu.nextples.ms_employee.exception.DuplicatePositionException;
import ru.nsu.nextples.ms_employee.exception.PositionNotFoundException;
import ru.nsu.nextples.ms_employee.model.Position;
import ru.nsu.nextples.ms_employee.model.EmployeeType;
import ru.nsu.nextples.ms_employee.repository.PositionRepository;
import ru.nsu.nextples.ms_employee.repository.specifications.PositionSpecifications;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PositionService {

    private final PositionRepository positionRepository;

    @Transactional
    public PositionDTO createPosition(PositionCreateDTO request) {
        if (positionRepository.existsByEmployeeTypeAndName(request.getEmployeeType(), request.getName())) {
            throw new DuplicatePositionException(request.getEmployeeType(), request.getName());
        }

        Position position = new Position();
        position.setName(request.getName());
        position.setEmployeeType(request.getEmployeeType());

        Position savedPosition = positionRepository.save(position);
        return mapToDTO(savedPosition);
    }

    @Transactional
    public PositionDTO updatePosition(UUID id, PositionUpdateDTO request) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new PositionNotFoundException(id));

        position.setName(request.getName());
        Position savedPosition = positionRepository.save(position);
        return mapToDTO(savedPosition);
    }

    @Transactional(readOnly = true)
    public Page<PositionDTO> getAllPositions(
            EmployeeType type,
            String name,
            Pageable pageable
    ) {
        Specification<Position> spec = Specification.where(null);
        if (name != null) {
            spec = spec.and(PositionSpecifications.nameContains(name));
        }
        if (type != null) {
            spec = spec.and(PositionSpecifications.hasEmployeeType(type));
        }

        return positionRepository.findAll(spec, pageable)
                .map(PositionService::mapToDTO);
    }

    @Transactional(readOnly = true)
    public PositionDTO getPosition(UUID id) {
        return positionRepository.findById(id)
                .map(PositionService::mapToDTO)
                .orElseThrow(() -> new PositionNotFoundException(id));
    }

    @Transactional
    public void deletePosition(UUID id) {
        if (!positionRepository.existsById(id)) {
            throw new PositionNotFoundException(id);
        }
        positionRepository.deleteById(id);
    }

    public static PositionDTO mapToDTO(Position position) {
        PositionDTO dto = new PositionDTO();
        dto.setId(position.getId());
        dto.setEmployeeType(position.getEmployeeType());
        dto.setName(position.getName());
        return dto;
    }
}
