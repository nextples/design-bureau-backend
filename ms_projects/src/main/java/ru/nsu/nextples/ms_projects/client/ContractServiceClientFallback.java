package ru.nsu.nextples.ms_projects.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ContractServiceClientFallback implements ContractServiceClient {

    @Override
    public ResponseEntity<List<UUID>> getProjectIdsByContractId(UUID contractId) {
        return ResponseEntity.ok(List.of());
    }
}
