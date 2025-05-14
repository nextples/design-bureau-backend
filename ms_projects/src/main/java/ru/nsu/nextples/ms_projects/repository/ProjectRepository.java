package ru.nsu.nextples.ms_projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nsu.nextples.ms_projects.model.Project;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

public interface ProjectRepository
        extends JpaRepository<Project, UUID>, JpaSpecificationExecutor<Project> {

    long countByIdIn(Collection<UUID> ids);
}