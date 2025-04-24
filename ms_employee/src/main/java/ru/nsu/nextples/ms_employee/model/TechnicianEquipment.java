package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "technician_equipment")
public class TechnicianEquipment {
    @EmbeddedId
    private TechnicianEquipmentId id;

    @MapsId("employeeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Technician employee;

}