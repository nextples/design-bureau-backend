package ru.nsu.nextples.ms_employee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "lab_assistants")
@DiscriminatorValue("LAB_ASSISTANT")
public class LabAssistant extends Employee {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratory_id")
    private Laboratory laboratory;
}