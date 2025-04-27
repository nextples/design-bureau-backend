package ru.nsu.nextples.ms_employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.nextples.ms_employee.model.EngineerSpecialization;

import java.util.UUID;

public interface EngineerSpecializationRepository extends JpaRepository<EngineerSpecialization, UUID> {
}
