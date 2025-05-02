package ru.nsu.nextples.ms_equipments.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.nextples.ms_equipments.model.EquipmentType;

import java.util.UUID;

public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, UUID> {
}
