package ru.nsu.nextples.ms_equipments.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("DEPARTMENT")
public class DepartmentAssignment extends Assignment {

    @Column(name = "department_id")
    private UUID departmentId;
}
