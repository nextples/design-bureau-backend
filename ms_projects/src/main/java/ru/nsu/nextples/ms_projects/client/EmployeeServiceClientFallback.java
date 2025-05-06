package ru.nsu.nextples.ms_projects.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EmployeeServiceClientFallback implements EmployeeServiceClient {

    @Override
    public ResponseEntity<Boolean> employeeExists(UUID employeeId) {
        return ResponseEntity.ok(false);
    }

    @Override
    public ResponseEntity<Boolean> employeeExists(List<UUID> employeeIds) {
        return ResponseEntity.ok(false);
    }

    @Override
    public ResponseEntity<Boolean> departmentExists(UUID departmentId) {
        return ResponseEntity.ok(false);
    }
}
