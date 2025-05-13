package ru.nsu.nextples.ms_projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nsu.nextples.ms_projects.model.Project;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface ProjectRepository
        extends JpaRepository<Project, UUID>, JpaSpecificationExecutor<Project> {

    @Query("SELECT SUM(p.cost) FROM Project p " +
            "WHERE p.status = 'COMPLETED' " +
            "AND p.startDate >= :start AND p.endDate <= :end")
    BigDecimal getTotalCostByPeriod(@Param("start") LocalDate start, @Param("end") LocalDate end);
}