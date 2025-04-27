package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(
        name = "employee_positions",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"employeeType", "name"}
        )
)
public class EmployeePosition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeType employeeType;

    @Column(nullable = false, length = 100)
    private String name;
}
