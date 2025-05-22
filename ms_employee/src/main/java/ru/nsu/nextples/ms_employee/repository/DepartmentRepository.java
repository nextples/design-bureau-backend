package ru.nsu.nextples.ms_employee.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.nextples.ms_employee.model.Department;

import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository
        extends JpaRepository<Department, UUID>, JpaSpecificationExecutor<Department> {

    @Override
    @EntityGraph(attributePaths = {"head", "employees"})
    Optional<Department> findById(UUID id);

    boolean existsByEmployees_Id(UUID departmentId);
}
