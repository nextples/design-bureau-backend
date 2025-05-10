package ru.nsu.nextples.ms_projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.nextples.ms_projects.model.Subcontractor;

import java.util.UUID;

public interface SubcontractorRepository
        extends JpaRepository<Subcontractor, UUID>, JpaSpecificationExecutor<Subcontractor> {

}