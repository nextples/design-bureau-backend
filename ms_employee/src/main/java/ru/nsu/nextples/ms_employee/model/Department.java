package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "department_id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_id")
    private ru.nsu.nextples.ms_employee.model.Employee head;

    @OneToMany(mappedBy = "department")
    private Set<ru.nsu.nextples.ms_employee.model.Employee> employees = new LinkedHashSet<>();

}