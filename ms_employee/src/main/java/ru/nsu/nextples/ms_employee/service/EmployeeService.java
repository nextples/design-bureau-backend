package ru.nsu.nextples.ms_employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeCreateDTO;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeDTO;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeUpdateDTO;
import ru.nsu.nextples.ms_employee.exception.EmployeeNotFoundException;
import ru.nsu.nextples.ms_employee.model.*;
import ru.nsu.nextples.ms_employee.repository.PositionRepository;
import ru.nsu.nextples.ms_employee.repository.EmployeeRepository;
import ru.nsu.nextples.ms_employee.repository.specifications.EmployeeSpecifications;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class EmployeeService {

    private final EmployeeFactory employeeFactory;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;

    @Transactional
    public EmployeeDTO createEmployee(EmployeeCreateDTO request) {
        Employee employee = employeeFactory.createEmployee(request);
        Employee savedEmployee = employeeRepository.save(employee);
        return mapToDTO(savedEmployee, true);
    }

    @Transactional
    public EmployeeDTO updateEmployee(UUID id, EmployeeUpdateDTO request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        employeeFactory.updateEmployee(employee, request);
        Employee savedEmployee = employeeRepository.save(employee);
        return mapToDTO(savedEmployee, true);
    }

    @Transactional(readOnly = true)
    public Page<EmployeeDTO> getAllEmployees(
            String firstName,
            String lastName,
            Integer ageFrom,
            Integer ageTo,
            EmployeeType employeeType,
            UUID positionId,
            UUID departmentId,
            Pageable pageable
    ) {
        Specification<Employee> spec = Specification.where(null);

        if (firstName != null) {
            spec.and(EmployeeSpecifications.firstNameContains(firstName));
        }
        if (lastName != null) {
            spec.and(EmployeeSpecifications.lastNameContains(lastName));
        }
        if (ageFrom != null) {
            spec.and(EmployeeSpecifications.hasAgeFrom(ageFrom));
        }
        if (ageTo != null) {
            spec.and(EmployeeSpecifications.hasAgeTo(ageTo));
        }
        if (employeeType != null) {
            spec.and(EmployeeSpecifications.hasEmployeeType(employeeType));
        }
        if (positionId != null) {
            spec.and(EmployeeSpecifications.hasPosition(positionId));
        }
        if (departmentId != null) {
            spec.and(EmployeeSpecifications.hasDepartment(departmentId));
        }

        return employeeRepository.findAll(spec, pageable)
                .map(employee -> mapToDTO(employee, false));
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeDetails(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return mapToDTO(employee, true);
    }

    @Transactional
    public void deleteEmployee(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeRepository.delete(employee);
    }

    public static EmployeeDTO mapToDTO(Employee employee, boolean detailed) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmployeeType(employee.getEmployeeType());
        dto.setPosition(PositionService.mapToDTO(employee.getPosition()));

        if (detailed) {
            dto.setAge(employee.getAge());
            dto.setDepartment(DepartmentService.mapToDTO(employee.getDepartment(), false));

            if (employee.getManagedDepartment() != null) {
                dto.setManagedDepartment(DepartmentService.mapToDTO(employee.getManagedDepartment(), false));
            }
            if (employee instanceof Designer designer) {
                dto.setPatentsCount(designer.getPatentsCount());
            }
            if (employee instanceof Engineer engineer) {
                dto.setSpecializations(engineer.getSpecializations()
                        .stream()
                        .map(EngineerSpecializationService::mapToDTO)
                        .collect(Collectors.toSet()));
            }
            if (employee instanceof Technician technician) {
                dto.setAssignedEquipment(technician.getAssignedEquipmentIds());
            }
            if (employee instanceof LabAssistant labAssistant) {
                dto.setLaboratory(LaboratoryService.mapToDTO(labAssistant.getLaboratory()));
            }
        }

        return dto;
    }
}
