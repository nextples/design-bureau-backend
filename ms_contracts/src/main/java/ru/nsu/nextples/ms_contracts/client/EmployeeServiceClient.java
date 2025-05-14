package ru.nsu.nextples.ms_contracts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.nsu.nextples.ms_contracts.config.FeignConfig;

import java.util.UUID;

@FeignClient(
        name = "${employee.service.url}",
        url = "${employee.service.url}",
        configuration = FeignConfig.class,
        fallback = EmployeeServiceClientFallback.class
)
public interface EmployeeServiceClient {

    @GetMapping("/api/v1/employee/{employeeId}/exists")
    ResponseEntity<Boolean> employeeExists(@PathVariable UUID employeeId);

    @GetMapping("/api/v1/departments/{departmentId}/exists")
    ResponseEntity<Boolean> departmentExists(@PathVariable UUID departmentId);
}
