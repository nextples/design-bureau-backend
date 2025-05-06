package ru.nsu.nextples.ms_projects.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "subcontractor_work")
public class SubcontractorWork {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "subcontractor_work_id")
    private UUID id;

    @Column(name = "work_percentage", nullable = false)
    private int workPercentage;

    @Column(name = "progress", nullable = false)
    private int progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcontractor_id")
    private Subcontractor subcontractor;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}