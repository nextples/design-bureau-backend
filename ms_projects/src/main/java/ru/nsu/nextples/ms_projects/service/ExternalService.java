package ru.nsu.nextples.ms_projects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_projects.client.ContractServiceClient;
import ru.nsu.nextples.ms_projects.client.EmployeeServiceClient;
import ru.nsu.nextples.ms_projects.client.EquipmentServiceClient;
import ru.nsu.nextples.ms_projects.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExternalService {

    private final ContractServiceClient contractService;
    private final EmployeeServiceClient employeeService;
    private final EquipmentServiceClient equipmentService;

    public void checkEmployeeExists(List<UUID> ids) {
        Boolean employeesExists = employeeService.employeeExists(ids).getBody();

        if (Boolean.FALSE.equals(employeesExists)) {
            throw new ObjectNotFoundException("Employee", ids);
        }
    }

    public void checkEquipmentExists(List<UUID> ids) {
        Map<UUID, Boolean> equipmentExistMap = equipmentService.equipmentExists(ids).getBody();

        if (equipmentExistMap == null || equipmentExistMap.containsValue(Boolean.FALSE)) {
            throw new ObjectNotFoundException("Equipment", ids);
        }
    }
}