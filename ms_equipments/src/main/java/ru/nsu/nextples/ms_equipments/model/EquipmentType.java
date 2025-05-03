package ru.nsu.nextples.ms_equipments.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "equipment_types")
public class EquipmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "type_id")
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private EquipmentCategory category;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
