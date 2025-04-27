package ru.nsu.nextples.ms_employee.service;

import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_employee.dto.EmployeePositionDTO;
import ru.nsu.nextples.ms_employee.model.EmployeePosition;

@Service
public class EmployeePositionService {

    public static EmployeePositionDTO mapToDTO(EmployeePosition employeePosition) {
        EmployeePositionDTO dto = new EmployeePositionDTO();
        dto.setId(employeePosition.getId());
        dto.setEmployeeType(employeePosition.getEmployeeType());
        dto.setName(employeePosition.getName());
        return dto;
    }
}
