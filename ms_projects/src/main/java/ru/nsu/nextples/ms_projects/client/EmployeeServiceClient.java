package ru.nsu.nextples.ms_projects.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nsu.nextples.ms_projects.config.FeignConfig;

import java.util.List;
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

    @GetMapping("/api/v1/employee/exists")
    ResponseEntity<Boolean> employeeExists(@RequestBody List<UUID> employeeIds);

    @GetMapping("/api/v1/departments/{departmentId}/exists")
    ResponseEntity<Boolean> departmentExists(@PathVariable UUID departmentId);
}
