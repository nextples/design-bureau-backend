package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "designers")
public class Designer extends Employee {

    @Column(name = "patents_count", nullable = false)
    private Integer patentsCount;
}