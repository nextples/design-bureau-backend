package ru.nsu.nextples.ms_equipments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.nsu.nextples.ms_equipments.client.ContractServiceClient;
import ru.nsu.nextples.ms_equipments.client.EmployeeServiceClient;
import ru.nsu.nextples.ms_equipments.client.ProjectServiceClient;
import ru.nsu.nextples.ms_equipments.exception.ObjectNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExternalService {

    private final ProjectServiceClient projectServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final ContractServiceClient contractServiceClient;

    public void checkDepartmentExists(@PathVariable UUID id) {
        Boolean departmentExists = employeeServiceClient.departmentExists(id).getBody();

        if (Boolean.FALSE.equals(departmentExists)) {
            throw new ObjectNotFoundException("Department", id);
        }
    }

    public void checkProjectExists(@PathVariable UUID id) {
        Boolean projectExists = projectServiceClient.projectExists(id).getBody();

        if (Boolean.FALSE.equals(projectExists)) {
            throw new ObjectNotFoundException("Project", id);
        }
    }

    public List<UUID> getProjectIdsByContractId(UUID contractId) {
        return contractServiceClient.getProjectIdsByContractId(contractId).getBody();
    }
}
