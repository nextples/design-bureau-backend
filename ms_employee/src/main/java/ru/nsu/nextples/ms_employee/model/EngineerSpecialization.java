package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

@Data
@Entity
@Table(name = "engineer_specializations")
public class EngineerSpecialization {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "specialization_id", nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;

    @ManyToMany(mappedBy = "specializations", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Engineer> engineers = new HashSet<>();

}