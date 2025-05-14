package ru.nsu.nextples.ms_contracts.client;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.nextples.ms_contracts.config.FeignConfig;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@FeignClient(
        name = "${project.service.url}",
        url = "${project.service.url}",
        configuration = FeignConfig.class,
        fallback = ProjectServiceClientFallback.class
)
public interface ProjectServiceClient {

    @GetMapping("/api/v1/projects/{projectId}/exists")
    ResponseEntity<Boolean> projectExists(@PathVariable UUID projectId);

    @GetMapping("/api/v1/projects/exists")
    ResponseEntity<Boolean> projectExists(@RequestParam List<UUID> projectIds);

    @GetMapping("/api/v1/projects/costs")
    ResponseEntity<Map<UUID, BigDecimal>> getProjectCosts(@RequestBody List<UUID> projectIds);
}
