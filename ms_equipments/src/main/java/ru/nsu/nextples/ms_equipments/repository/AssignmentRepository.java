package ru.nsu.nextples.ms_equipments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.nextples.ms_equipments.model.Equipment;

import java.util.UUID;

public interface AssignmentRepository
        extends JpaRepository<Equipment, UUID>, JpaSpecificationExecutor<Equipment> {

}
