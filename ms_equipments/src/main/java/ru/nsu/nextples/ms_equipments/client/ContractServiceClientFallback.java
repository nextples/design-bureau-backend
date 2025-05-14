package ru.nsu.nextples.ms_equipments.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Component
public class ContractServiceClientFallback implements ContractServiceClient {

    @Override
    public ResponseEntity<List<UUID>> getProjectIdsByContractId(UUID contractId) {
        return ResponseEntity.ok(List.of());
    }
}
