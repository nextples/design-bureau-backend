package ru.nsu.nextples.ms_equipments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "equipments")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "equipment_id")
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "serial_number", length = 10)
    @Pattern(regexp = "^EQ-[A-Z]{3}\\d{4}$",
            message = "Серийный номер должен быть записан в формате EQ-AAA0000" +
                    "(AAA = заглавные латинские буквы, 0000 = цифры)"
    )
    private String serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private EquipmentType type;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "current_department_id")
    private UUID currentDepartmentId;

    @Column(name = "current_project_id")
    private UUID currentProjectId;

    @Column(name = "owner_department_id")
    private UUID ownerDepartmentId;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable = true;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    private List<Assignment> assignments = new ArrayList<>();

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
