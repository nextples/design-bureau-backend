package ru.nsu.nextples.ms_equipments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_equipments.dto.type.EquipmentTypeDTO;
import ru.nsu.nextples.ms_equipments.model.EquipmentType;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EquipmentTypeService {

    public static EquipmentTypeDTO mapToDTO(EquipmentType entity) {
        EquipmentTypeDTO dto = new EquipmentTypeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory());
        return dto;
    }
}
