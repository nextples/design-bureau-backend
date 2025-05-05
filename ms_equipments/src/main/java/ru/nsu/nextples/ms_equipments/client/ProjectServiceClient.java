package ru.nsu.nextples.ms_equipments.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.nsu.nextples.ms_equipments.config.FeignConfig;

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
}
