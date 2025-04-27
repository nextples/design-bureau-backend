package ru.nsu.nextples.ms_employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.nextples.ms_employee.model.Department;
import ru.nsu.nextples.ms_employee.model.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository
        extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {

}
