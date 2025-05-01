package ru.nsu.nextples.ms_equipments.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.nextples.ms_equipments.dto.EquipmentCreateDTO;
import ru.nsu.nextples.ms_equipments.dto.EquipmentDTO;

@RestController
@RequestMapping("/api/equipments")
public class EquipmentController {

    @PostMapping
    public EquipmentDTO create(EquipmentCreateDTO request) {
        return new EquipmentDTO();
    }
}
