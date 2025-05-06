package ru.nsu.nextples.ms_projects.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "subcontractors")
public class Subcontractor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "subcontractor_id")
    private UUID id;

    @Column(name = "company_name", unique = true, nullable = false)
    private String companyName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "subcontractor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubcontractorWork> works = new ArrayList<>();
}
