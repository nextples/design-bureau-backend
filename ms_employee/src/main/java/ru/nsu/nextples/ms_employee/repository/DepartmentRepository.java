package ru.nsu.nextples.ms_employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.nextples.ms_employee.model.Department;

import java.util.UUID;

public interface DepartmentRepository
        extends JpaRepository<Department, UUID>, JpaSpecificationExecutor<Department> {

    boolean existsByEmployees_Id(UUID departmentId);
}
