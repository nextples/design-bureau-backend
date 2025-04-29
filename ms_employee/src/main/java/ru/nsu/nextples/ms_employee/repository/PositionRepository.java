package ru.nsu.nextples.ms_employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.nextples.ms_employee.model.Position;
import ru.nsu.nextples.ms_employee.model.EmployeeType;

import java.util.UUID;

public interface PositionRepository
        extends JpaRepository<Position, UUID>, JpaSpecificationExecutor<Position> {

    boolean existsByEmployeeTypeAndName(EmployeeType type, String name);
}
