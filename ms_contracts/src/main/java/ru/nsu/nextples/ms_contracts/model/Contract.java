package ru.nsu.nextples.ms_contracts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "contract_id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "manager_id", nullable = false)
    private UUID managerId;

    @ElementCollection
    @CollectionTable(name = "contract_projects", joinColumns = @JoinColumn(name = "contract_id"))
    @Column(name = "project_id")
    private List<UUID> projectIds = new ArrayList<>();

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
