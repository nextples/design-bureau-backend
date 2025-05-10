package ru.nsu.nextples.ms_projects.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nsu.nextples.ms_projects.config.FeignConfig;
import ru.nsu.nextples.ms_projects.dto.equipment.AssignmentDTO;
import ru.nsu.nextples.ms_projects.dto.equipment.AssignmentRequestDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@FeignClient(
        name = "${equipment.service.url}",
        url = "${equipment.service.url}",
        configuration = FeignConfig.class,
        fallback = EquipmentServiceClientFallback.class
)
public interface EquipmentServiceClient {

    @PutMapping("/api/v1/equipment/exists")
    ResponseEntity<Map<UUID, Boolean>> equipmentExists(@RequestBody List<UUID> equipmentId);

    @PostMapping("/api/v1/assignments/project/{equipmentId}")
    ResponseEntity<AssignmentDTO> assignEquipmentToProject(@PathVariable UUID equipmentId,
                                                           @RequestBody AssignmentRequestDTO request);

}