package ru.nsu.nextples.ms_employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_employee.client.EquipmentServiceClient;
import ru.nsu.nextples.ms_employee.exception.InvalidEquipmentException;
import ru.nsu.nextples.ms_employee.exception.ServiceUnavailableException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExistenceService {

    private final EquipmentServiceClient equipmentServiceClient;

    public void validateEquipment(Set<UUID> equipmentIds) {
        if (equipmentIds == null || equipmentIds.isEmpty()) {
            return;
        }

        ResponseEntity<Map<UUID, Boolean>> response =
                equipmentServiceClient.checkEquipmentExists(equipmentIds);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ServiceUnavailableException("Equipment service unavailable");
        }

        Map<UUID, Boolean> existenceMap = response.getBody();
        List<UUID> invalidIds = equipmentIds.stream()
                .filter(id -> {
                    assert existenceMap != null;
                    return !existenceMap.getOrDefault(id, false);
                })
                .collect(Collectors.toList());

        if (!invalidIds.isEmpty()) {
            throw new InvalidEquipmentException(invalidIds);
        }
    }
}
