package ru.nsu.nextples.ms_equipments.client;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class ContractServiceClientFallback implements ContractServiceClient {

    @Override
    public ResponseEntity<List<UUID>> getProjectIdsByContractId(UUID contractId) {
        return ResponseEntity.ok(List.of());
    }
}
