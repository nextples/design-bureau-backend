package ru.nsu.nextples.ms_equipments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.nextples.ms_equipments.model.Assignment;

import java.util.UUID;

public interface AssignmentRepository
        extends JpaRepository<Assignment, UUID>, JpaSpecificationExecutor<Assignment> {

}
