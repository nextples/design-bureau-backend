package ru.nsu.nextples.ms_projects.model;

import jakarta.persistence.*;
import jakarta.validation.ValidationException;
import lombok.Data;
import ru.nsu.nextples.ms_projects.service.ProjectService;

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

    @Column(name = "internal_progress")
    private int internalProgress;       // 0-100% (прогресс работ, выполняемых компанией)

    @Column(name = "total_progress")
    private int totalProgress;          // 0-100% (общий прогресс)

    @Column(name = "responsible_department_id", nullable = false)
    private UUID responsibleDepartmentId;

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



    @PreUpdate
    @PrePersist
    private void validate() {
        if (ProjectService.getTotalSubcontractedPercentage(this) > 100) {
            throw new ValidationException("Subcontracted work cannot exceed 100%");
        }
        if (internalProgress < 0 || internalProgress > 100) {
            throw new ValidationException("Internal progress must be between 0 and 100");
        }
    }
}