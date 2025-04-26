package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "employee_positions")
public class EmployeePosition {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "position_id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "position")
    private Set<Employee> employees = new LinkedHashSet<>();
}