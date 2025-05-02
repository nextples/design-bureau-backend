package ru.nsu.nextples.ms_equipments.service;

import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_equipments.dto.maintenance.MaintenanceRecordDTO;
import ru.nsu.nextples.ms_equipments.model.MaintenanceRecord;

@Service
public class MaintenanceService {

    public static MaintenanceRecordDTO mapToDTO(MaintenanceRecord entity) {
        MaintenanceRecordDTO dto = new MaintenanceRecordDTO();
        dto.setId(entity.getId());
        dto.setEquipment(EquipmentService.mapToDTO(entity.getEquipment(), false));
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setCost(entity.getCost());
        dto.setServiceCompany(entity.getServiceCompany());
        return dto;
    }
}
