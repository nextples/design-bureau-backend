package ru.nsu.nextples.ms_equipments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nsu.nextples.ms_equipments.dto.report.EfficiencyDTO;
import ru.nsu.nextples.ms_equipments.model.Equipment;

import java.util.List;
import java.util.UUID;

public interface EquipmentRepository
        extends JpaRepository<Equipment, UUID>, JpaSpecificationExecutor<Equipment> {

    @Query("SELECT e, COALESCE(SUM(a.hoursUsed), 0) " +
            "FROM Equipment e " +
            "LEFT JOIN e.assignments a ON TYPE(a) = ProjectAssignment " +
            "WHERE e.id = :equipmentId " +
            "GROUP BY e")
    List<Object[]> findEquipmentEfficiencyData(@Param("equipmentId") UUID equipmentId);

}