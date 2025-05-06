package ru.nsu.nextples.ms_equipments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_equipments.dto.type.EquipmentTypeCreateDTO;
import ru.nsu.nextples.ms_equipments.dto.type.EquipmentTypeDTO;
import ru.nsu.nextples.ms_equipments.dto.type.EquipmentTypeUpdateDTO;
import ru.nsu.nextples.ms_equipments.exception.DeleteConflictException;
import ru.nsu.nextples.ms_equipments.exception.ObjectNotFoundException;
import ru.nsu.nextples.ms_equipments.model.EquipmentType;
import ru.nsu.nextples.ms_equipments.repository.EquipmentTypeRepository;
import ru.nsu.nextples.ms_equipments.repository.specifications.EquipmentTypeSpecifications;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EquipmentTypeService {

    private final EquipmentTypeRepository typeRepository;

    @Transactional
    public EquipmentTypeDTO createEquipmentType(EquipmentTypeCreateDTO request) {
        EquipmentType type = new EquipmentType();
        type.setName(request.getName());
        type.setDescription(request.getDescription());
        type.setCategory(request.getCategory());
        EquipmentType savedType = typeRepository.save(type);
        return mapToDTO(savedType);
    }

    @Transactional
    public EquipmentTypeDTO updateEquipmentType(UUID id, EquipmentTypeUpdateDTO request) {
        EquipmentType type = typeRepository.findOne(EquipmentTypeSpecifications.notDeleted(id))
                .orElseThrow(() -> new ObjectNotFoundException("Equipment Type", id));

        if (request.getName() != null) {
            type.setName(request.getName());
        }
        if (request.getDescription() != null) {
            type.setDescription(request.getDescription());
        }
        if (request.getCategory() != null) {
            type.setCategory(request.getCategory());
        }
        EquipmentType savedType = typeRepository.save(type);
        return mapToDTO(savedType);
    }

    @Transactional(readOnly = true)
    public EquipmentTypeDTO getEquipmentType(UUID id) {
        EquipmentType type = typeRepository.findOne(EquipmentTypeSpecifications.notDeleted(id))
                .orElseThrow(() -> new ObjectNotFoundException("EquipmentType", id));
        return mapToDTO(type);
    }

    @Transactional(readOnly = true)
    public Page<EquipmentTypeDTO> getEquipmentTypes(Pageable pageable) {
        Page<EquipmentType> types = typeRepository.findAll(EquipmentTypeSpecifications.notDeleted(), pageable);
        return types.map(EquipmentTypeService::mapToDTO);
    }

    @Transactional
    public void softDeleteEquipmentType(UUID id) {
        EquipmentType type = typeRepository.findOne(EquipmentTypeSpecifications.canBeSafelyDeleted(id))
                .orElseThrow(() -> {
                    boolean exists = typeRepository.existsById(id);
                    if (!exists) {
                        return new ObjectNotFoundException("EquipmentType", id);
                    }
                    throw new DeleteConflictException("EquipmentType", id);
                });
        type.setDeleted(true);
        typeRepository.save(type);
    }

    public static EquipmentTypeDTO mapToDTO(EquipmentType entity) {
        EquipmentTypeDTO dto = new EquipmentTypeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory());
        return dto;
    }
}
