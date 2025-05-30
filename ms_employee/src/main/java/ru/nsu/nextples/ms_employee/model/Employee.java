package ru.nsu.nextples.ms_employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import ru.nsu.nextples.ms_employee.annotation.ValidPosition;

import java.util.UUID;

@Data
@Entity
@Table(name = "employees")
@Inheritance(strategy = InheritanceType.JOINED)
@ValidPosition
@DiscriminatorColumn(
        name = "employee_type",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "employee_id", nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", insertable = false, updatable = false)
    private EmployeeType employeeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_position_id", nullable = false)
    private Position position;

    @Column(name = "age")
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(mappedBy = "head")
    @JsonIgnore
    private Department managedDepartment;
}