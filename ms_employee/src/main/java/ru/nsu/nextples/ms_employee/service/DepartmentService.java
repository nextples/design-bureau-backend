package ru.nsu.nextples.ms_employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_employee.dto.department.DepartmentCreateDTO;
import ru.nsu.nextples.ms_employee.dto.department.DepartmentDTO;
import ru.nsu.nextples.ms_employee.dto.department.DepartmentUpdateDTO;
import ru.nsu.nextples.ms_employee.exception.DepartmentHasEmployeesDeleteException;
import ru.nsu.nextples.ms_employee.exception.DepartmentNotFoundException;
import ru.nsu.nextples.ms_employee.exception.EmployeeNotFoundException;
import ru.nsu.nextples.ms_employee.exception.InvalidNameException;
import ru.nsu.nextples.ms_employee.model.Department;
import ru.nsu.nextples.ms_employee.model.Employee;
import ru.nsu.nextples.ms_employee.repository.DepartmentRepository;
import ru.nsu.nextples.ms_employee.repository.EmployeeRepository;
import ru.nsu.nextples.ms_employee.repository.specifications.DepartmentSpecifications;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentDTO createDepartment(DepartmentCreateDTO request) {
        validateHead(request.getHeadId());

        Department department = new Department();
        setDepartmentName(department, request.getName());
        setDepartmentHead(department, request.getHeadId());

        return mapToDTO(department, true);
    }

    public DepartmentDTO updateDepartment(UUID id, DepartmentUpdateDTO request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        validateHead(request.getHeadId());
        setDepartmentHead(department, request.getHeadId());
        setDepartmentName(department, request.getName());

        return mapToDTO(department, true);
    }

    public Page<DepartmentDTO> getAllDepartments(String name, UUID headId, Pageable pageable) {
        Specification<Department> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and(DepartmentSpecifications.nameContains(name));
        }
        if (headId != null) {
            spec = spec.and(DepartmentSpecifications.hasHead(headId));
        }

        return departmentRepository.findAll(spec, pageable)
                .map(department -> mapToDTO(department, false));
    }

    public DepartmentDTO getDepartmentDetails(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        return mapToDTO(department, true);
    }

    public void deleteDepartment(UUID id) {
        if (departmentRepository.existsByEmployees_Id(id)) {
            throw new DepartmentHasEmployeesDeleteException(id);
        }
        departmentRepository.deleteById(id);
    }

    public static DepartmentDTO mapToDTO(Department department, boolean detailed) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setTotalEmployees(department.getEmployees().size());
        dto.setHead(EmployeeService.mapToDTO(department.getHead(), false));
        if (detailed) {
            dto.setEmployees(department.getEmployees().stream()
                    .map(employee -> EmployeeService.mapToDTO(employee, false))
                    .collect(Collectors.toSet()));
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
