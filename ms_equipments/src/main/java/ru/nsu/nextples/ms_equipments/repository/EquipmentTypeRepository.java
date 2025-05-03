package ru.nsu.nextples.ms_equipments.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.nextples.ms_equipments.model.EquipmentType;

import java.util.UUID;

public interface EquipmentTypeRepository
        extends JpaRepository<EquipmentType, UUID>, JpaSpecificationExecutor<EquipmentType> {
}
