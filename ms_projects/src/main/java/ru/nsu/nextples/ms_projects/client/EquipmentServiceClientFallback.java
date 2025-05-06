package ru.nsu.nextples.ms_projects.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class EquipmentServiceClientFallback implements EquipmentServiceClient {

    @Override
    public ResponseEntity<Map<UUID, Boolean>> equipmentExists(List<UUID> equipmentIds) {
        Map<UUID, Boolean> map = new HashMap<>();
        for (UUID equipmentId : equipmentIds) {
            map.put(equipmentId, false);
        }
        return ResponseEntity.ok(map);
    }
}
