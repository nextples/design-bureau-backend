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
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "employee_id", nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private EmployeeCategory category;

    @Column(name = "age")
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "head")
    private Set<Department> departments = new LinkedHashSet<>();

    @OneToOne
    private Designer designer;

    @ManyToMany
    private Set<ru.nsu.nextples.ms_employee.model.EngineerSpecialization> engineerSpecializations = new LinkedHashSet<>();

    @ManyToMany
    private Set<ru.nsu.nextples.ms_employee.model.Laboratory> laboratories = new LinkedHashSet<>();

    @OneToOne
    private ru.nsu.nextples.ms_employee.model.Technician technician;

}