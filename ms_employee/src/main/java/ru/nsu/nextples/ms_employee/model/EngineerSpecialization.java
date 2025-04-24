package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "engineer_specializations")
public class EngineerSpecialization {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "specialization_id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToMany(mappedBy = "engineerSpecializations")
    private Set<Employee> employees = new LinkedHashSet<>();

}