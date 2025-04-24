package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
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

    @OneToOne(mappedBy = "head")
    private Department departmentAsHead;

    @OneToOne
    private Designer designer;

    @ManyToMany
    private Set<EngineerSpecialization> engineerSpecializations = new LinkedHashSet<>();

    @ManyToMany
    private Set<Laboratory> laboratories = new LinkedHashSet<>();

    @OneToOne
    private Technician technician;

}