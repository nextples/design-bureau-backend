package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "technician_equipment")
public class TechnicianEquipment {
    @EmbeddedId
    private TechnicianEquipmentId id;

    @MapsId("employeeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private ru.nsu.nextples.ms_employee.model.Technician employee;

}