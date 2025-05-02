package ru.nsu.nextples.ms_equipments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.nextples.ms_equipments.model.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
