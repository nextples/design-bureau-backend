package ru.nsu.nextples.ms_contracts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_contracts.client.EmployeeServiceClient;
import ru.nsu.nextples.ms_contracts.client.ProjectServiceClient;
import ru.nsu.nextples.ms_contracts.exception.ObjectNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExternalService {

    private final EmployeeServiceClient employeeService;
    private final ProjectServiceClient projectService;

    public void checkEmployeeExists(UUID id) {
        Boolean employeesExists = employeeService.employeeExists(id).getBody();

        if (Boolean.FALSE.equals(employeesExists)) {
            throw new ObjectNotFoundException("Employee", id);
        }
    }

    public void checkProjectsExists(List<UUID> ids) {
        Boolean projectExists = projectService.projectExists(ids).getBody();

        if (Boolean.FALSE.equals(projectExists)) {
            throw new ObjectNotFoundException("Project", ids);
        }
    }

    public Map<UUID, BigDecimal> getProjectsCosts(List<UUID> ids) {
        return projectService.getProjectCosts(ids).getBody();
    }
}
