package ru.nsu.nextples.ms_equipments.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "project_assignments")
public class ProjectAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "assignment_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "assignment_date", nullable = false)
    private LocalDateTime assignmentDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "hours_used")
    private Integer hoursUsed;

    @Column(length = 500)
    private String purpose;
}
