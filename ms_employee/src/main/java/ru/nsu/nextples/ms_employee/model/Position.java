package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(
        name = "positions",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"employee_type", "name"}
        )
)
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "position_id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = false)
    private EmployeeType employeeType;

    @Column(nullable = false, length = 100)
    private String name;
}
