package ru.nsu.nextples.ms_employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.nextples.ms_employee.model.EmployeePosition;

import java.util.UUID;

public interface EmployeeCategoryRepository extends JpaRepository<EmployeePosition, UUID> {

}
