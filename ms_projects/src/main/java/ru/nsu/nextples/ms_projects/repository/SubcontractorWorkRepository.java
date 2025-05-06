package ru.nsu.nextples.ms_projects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.nsu.nextples.ms_projects.model.SubcontractorWork;

import java.util.UUID;

public interface SubcontractorWorkRepository
        extends JpaRepository<SubcontractorWork, UUID>, JpaSpecificationExecutor<SubcontractorWork> {

}