package ru.nsu.nextples.ms_employee.service;

import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import ru.nsu.nextples.ms_employee.dto.department.DepartmentCreateDTO;
import ru.nsu.nextples.ms_employee.dto.department.DepartmentDTO;
import ru.nsu.nextples.ms_employee.dto.department.DepartmentUpdateDTO;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeDTO;
import ru.nsu.nextples.ms_employee.exception.DepartmentHasEmployeesDeleteException;
import ru.nsu.nextples.ms_employee.exception.DepartmentNotFoundException;
import ru.nsu.nextples.ms_employee.exception.EmployeeNotFoundException;
import ru.nsu.nextples.ms_employee.exception.InvalidNameException;
import ru.nsu.nextples.ms_employee.model.Department;
import ru.nsu.nextples.ms_employee.model.Employee;
import ru.nsu.nextples.ms_employee.repository.DepartmentRepository;
import ru.nsu.nextples.ms_employee.repository.EmployeeRepository;
import ru.nsu.nextples.ms_employee.repository.specifications.DepartmentSpecifications;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public Boolean checkIfDepartmentExists(@PathVariable UUID id) {
        return departmentRepository.existsById(id);
    }

    @Transactional
    public DepartmentDTO createDepartment(DepartmentCreateDTO request) {
        validateHead(request.getHeadId());

        Department department = new Department();
        setDepartmentName(department, request.getName());
        setDepartmentHead(department, request.getHeadId());

        Department savedDepartment = departmentRepository.save(department);
        return mapToDTO(savedDepartment, true);
    }

    @Transactional
    public DepartmentDTO updateDepartment(UUID id, DepartmentUpdateDTO request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        if (request.getHeadId() != null) {
            validateHead(request.getHeadId());
            setDepartmentHead(department, request.getHeadId());
        }

        if (request.getName() != null) {
            setDepartmentName(department, request.getName());
        }
        Department updatedDepartment = departmentRepository.save(department);
        return mapToDTO(updatedDepartment, true);
    }

    @Transactional(readOnly = true)
    @EntityGraph(attributePaths = {"employees"})
    public List<DepartmentDTO> getAllDepartments(String name, UUID headId) {
        Specification<Department> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and(DepartmentSpecifications.nameContains(name));
        }
        if (headId != null) {
            spec = spec.and(DepartmentSpecifications.hasHead(headId));
        }

        spec = spec.and((root, query, cb) -> {
            root.fetch("employees", JoinType.LEFT);
            return null;
        });

        List<Department> departments = departmentRepository.findAll(spec);

        return departments
                .stream()
                .map(department -> mapToDTO(department, false)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DepartmentDTO getDepartmentDetails(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        return mapToDTO(department, true);
    }

    @Transactional
    public void deleteDepartment(UUID id) {
        if (departmentRepository.existsByEmployees_Id(id)) {
            throw new DepartmentHasEmployeesDeleteException(id);
        }
        departmentRepository.deleteById(id);
    }

    public static DepartmentDTO mapToDTO(Department department, boolean detailed) {
        if (department == null) {
            return null;
        }

        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setHead(EmployeeService.mapToDTO(department.getHead(), false));

        Set<EmployeeDTO> employees = department.getEmployees().stream()
                .map(employee -> EmployeeService.mapToDTO(employee, false))
                .collect(Collectors.toSet());
        dto.setTotalEmployees(employees.size());

        if (detailed) {
            dto.setEmployees(employees);
        }
        return dto;
    }

    private void validateHead(UUID headId) {
        if (headId != null && !employeeRepository.existsById(headId)) {
            throw new EmployeeNotFoundException(headId);
        }
    }

    private void setDepartmentHead(Department department, UUID headId) {
        if (headId == null) {
            department.setHead(null);
            return;
        }

        Employee head = employeeRepository.findById(headId)
                .orElseThrow(() -> new EmployeeNotFoundException(headId));

        department.setHead(head);
    }

    private void setDepartmentName(Department department, String name) {
        if (name == null) {
            throw new InvalidNameException("Department name cannot be null or empty");
        }

        department.setName(name);
    }
}
