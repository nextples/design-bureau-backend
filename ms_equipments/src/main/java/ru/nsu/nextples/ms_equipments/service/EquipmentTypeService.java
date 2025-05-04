package ru.nsu.nextples.ms_equipments.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_equipments.dto.type.EquipmentTypeCreateDTO;
import ru.nsu.nextples.ms_equipments.dto.type.EquipmentTypeDTO;
import ru.nsu.nextples.ms_equipments.model.EquipmentType;
import ru.nsu.nextples.ms_equipments.repository.EquipmentTypeRepository;

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

    public static EquipmentTypeDTO mapToDTO(EquipmentType entity) {
        EquipmentTypeDTO dto = new EquipmentTypeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory());
        return dto;
    }
}
