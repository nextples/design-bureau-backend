package ru.nsu.nextples.ms_employee.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nsu.nextples.ms_employee.config.FeignConfig;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@FeignClient(
        name = "${equipment.service.url}",
        url = "${equipment.service.url}",
        configuration = FeignConfig.class,
        fallback = EquipmentServiceClientFallback.class
)
public interface EquipmentServiceClient {

    @PutMapping("/api/v1/equipment/exists")
    ResponseEntity<Map<UUID, Boolean>> checkEquipmentExists(@RequestBody Set<UUID> equipmentId);
}
