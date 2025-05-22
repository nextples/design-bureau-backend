package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "technicians")
@DiscriminatorValue("TECHNICIAN")
public class Technician extends Employee {

    @ElementCollection
    @CollectionTable(
            name = "technician_equipment",
            joinColumns = @JoinColumn(name = "employee_id")
    )
    @Column(name = "equipment_id")
    private Set<UUID> assignedEquipmentIds = new HashSet<>();
}