package ru.nsu.nextples.ms_projects.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "project_id")
    private UUID id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectStatus status;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "progress")
    private Integer progress;       // 0-100%

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ElementCollection
    @CollectionTable(name = "project_employees", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "employee_id")
    private List<UUID> employeeIds = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "project_equipment", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "equipment_id")
    private List<UUID> equipmentIds = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "project_contracts", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "contract_id")
    private List<UUID> contractIds = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubcontractorWork> subcontractorWorks = new ArrayList<>();
}