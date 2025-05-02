package ru.nsu.nextples.ms_equipments.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentCreateDTO;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentDTO;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    @PostMapping
    public EquipmentDTO create(EquipmentCreateDTO request) {
        return new EquipmentDTO();
    }
}
