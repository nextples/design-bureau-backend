package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Составной внешний ключ таблицы technician_equipment
 */
@Data
@Embeddable
public class TechnicianEquipmentId implements Serializable {
    private static final long serialVersionUID = 7316726498822513618L;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "equipment_id", nullable = false)
    private UUID equipmentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TechnicianEquipmentId entity = (TechnicianEquipmentId) o;
        return Objects.equals(this.employeeId, entity.employeeId) &&
                Objects.equals(this.equipmentId, entity.equipmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, equipmentId);
    }

}