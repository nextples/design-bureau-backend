package ru.nsu.nextples.ms_contracts.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class ProjectServiceClientFallback implements ProjectServiceClient {

    @Override
    public ResponseEntity<Boolean> projectExists(UUID projectId) {
        return ResponseEntity.ok(false);
    }

    @Override
    public ResponseEntity<Boolean> projectExists(List<UUID> projectIds) {
        return ResponseEntity.ok(false);
    }

    @Override
    public ResponseEntity<Map<UUID, BigDecimal>> getProjectCosts(List<UUID> projectIds) {
        return ResponseEntity.ok(new HashMap<>());
    }
}
