package ru.nsu.nextples.ms_projects.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nsu.nextples.ms_projects.config.FeignConfig;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "${contract.service.url}",
        url = "${contract.service.url}",
        configuration = FeignConfig.class,
        fallback = ContractServiceClientFallback.class
)
public interface ContractServiceClient {

    @GetMapping("api/v1/contracts/{contractId}/projects")
    ResponseEntity<List<UUID>> getProjectIdsByContractId(UUID contractId);
}
