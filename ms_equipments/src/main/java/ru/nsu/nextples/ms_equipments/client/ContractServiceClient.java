package ru.nsu.nextples.ms_equipments.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.nsu.nextples.ms_equipments.config.FeignConfig;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "${contract.service.url}",
        url = "${contract.service.url}",
        configuration = FeignConfig.class,
        fallback = ContractServiceClientFallback.class
)
public interface ContractServiceClient {

    @GetMapping("api/v1/contracts/projects/{contractId}")
    ResponseEntity<List<UUID>> getProjectIdsByContractId(@PathVariable UUID contractId);
}
