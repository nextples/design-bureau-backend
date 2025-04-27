package ru.nsu.nextples.ms_employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.nextples.ms_employee.model.Laboratory;

import java.util.UUID;

public interface LaboratoryRepository extends JpaRepository<Laboratory, UUID> {
}
