package ru.nsu.nextples.ms_equipments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.nsu.nextples.ms_equipments.client.EmployeeServiceClient;
import ru.nsu.nextples.ms_equipments.exception.ObjectNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExistenceService {
    private final EmployeeServiceClient employeeServiceClient;

    public void checkDepartmentExists(@PathVariable UUID id) {
        Boolean departmentExists = employeeServiceClient.departmentExists(id)
                .getBody();

        if (Boolean.FALSE.equals(departmentExists)) {
            throw new ObjectNotFoundException("Department", id);
        }
    }
}
