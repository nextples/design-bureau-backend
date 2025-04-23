package ru.nsu.nextples.ms_employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_employee.dto.EmployeeRequest;
import ru.nsu.nextples.ms_employee.exception.EmployeeNotFoundException;
import ru.nsu.nextples.ms_employee.model.Employee;
import ru.nsu.nextples.ms_employee.repository.DepartmentRepository;
import ru.nsu.nextples.ms_employee.repository.EmployeeCategoryRepository;
import ru.nsu.nextples.ms_employee.repository.EmployeeRepository;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeCategoryRepository employeeCategoryRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(UUID id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + "not found"));
    }

    public List<Employee> findByDepartmentId(UUID departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    public List<Employee> findByEmployeeCategoryId(UUID employeeCategoryId) {
        return employeeRepository.findByCategoryId(employeeCategoryId);
    }

    public List<Employee> findByAge(int age) {
        return employeeRepository.findByAge(age);
    }

    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        return employeeRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Employee create(EmployeeRequest request) {

    }
}
