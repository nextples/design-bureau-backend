package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "engineers")
@DiscriminatorValue("ENGINEER")
public class Engineer extends Employee {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "engineer_specialization_links",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    @ToString.Exclude
    private List<EngineerSpecialization> specializations = new ArrayList<>();
}