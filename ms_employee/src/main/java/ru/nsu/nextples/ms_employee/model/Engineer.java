package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "engineers")
public class Engineer extends Employee {

    @ManyToMany
    @JoinTable(
            name = "engineer_specializations",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Set<EngineerSpecialization> specializations = new HashSet<>();
}