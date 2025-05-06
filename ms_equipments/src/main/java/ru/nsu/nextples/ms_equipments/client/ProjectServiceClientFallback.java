package ru.nsu.nextples.ms_equipments.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProjectServiceClientFallback implements ProjectServiceClient {

    @Override
    public ResponseEntity<Boolean> projectExists(UUID projectId) {
        return ResponseEntity.ok(false);
    }
}
