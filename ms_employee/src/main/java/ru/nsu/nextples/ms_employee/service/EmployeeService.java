package ru.nsu.nextples.ms_employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeDTO;
import ru.nsu.nextples.ms_employee.dto_old.employee.EmployeeCreateRequestDTO;
import ru.nsu.nextples.ms_employee.exception.EmployeeNotFoundException;
import ru.nsu.nextples.ms_employee.model.*;
import ru.nsu.nextples.ms_employee.repository.DepartmentRepository;
import ru.nsu.nextples.ms_employee.repository.EmployeeCategoryRepository;
import ru.nsu.nextples.ms_employee.repository.EmployeeRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeCategoryRepository employeeCategoryRepository;
    private final DepartmentRepository departmentRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(UUID id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public List<Employee> findByDepartmentId(UUID departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

//    public List<Employee> findByEmployeeCategoryId(UUID employeeCategoryId) {
//        return employeeRepository.findByCategoryId(employeeCategoryId);
//    }

    public List<Employee> findByAge(int age) {
        return employeeRepository.findByAge(age);
    }

    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        return employeeRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Employee create(EmployeeCreateRequestDTO request) {
        return null;
    }

    public static EmployeeDTO mapToDTO(Employee employee, boolean detailed) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setPosition(EmployeePositionService.mapToDTO(employee.getPosition()));

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
