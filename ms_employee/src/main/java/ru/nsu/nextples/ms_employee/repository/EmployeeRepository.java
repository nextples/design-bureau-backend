package ru.nsu.nextples.ms_employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.nextples.ms_employee.model.Department;
import ru.nsu.nextples.ms_employee.model.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByDepartmentId(UUID departmentId);
    List<Employee> findByCategoryId(UUID categoryId);
    List<Employee> findByAge(int age);
    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);
}
