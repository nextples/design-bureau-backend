package ru.nsu.nextples.ms_employee.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.nsu.nextples.ms_employee.exception.ServiceUnavailableException;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class EquipmentServiceClientFallback implements EquipmentServiceClient {

    @Override
    public ResponseEntity<Map<UUID, Boolean>> checkEquipmentExists(Set<UUID> equipmentIds) {
        throw new ServiceUnavailableException("Equipment service unavailable");
    }
}
