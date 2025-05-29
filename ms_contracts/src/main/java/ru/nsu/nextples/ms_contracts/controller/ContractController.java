package ru.nsu.nextples.ms_contracts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.nextples.ms_contracts.dto.contract.ContractCreateDTO;
import ru.nsu.nextples.ms_contracts.dto.contract.ContractDTO;
import ru.nsu.nextples.ms_contracts.dto.contract.ContractUpdateDTO;
import ru.nsu.nextples.ms_contracts.service.ContractService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contracts")
@Tag(name = "Договоры", description = "API для управления договорами")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    @Operation(summary = "Создать договор")
    public ResponseEntity<ContractDTO> createContract(@Valid @RequestBody ContractCreateDTO request) {
        ContractDTO contract = contractService.createContract(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(contract);
    }

    @PutMapping("/{contractId}")
    @Operation(summary = "Обновить договор")
    public ResponseEntity<ContractDTO> updateContract(@Valid @PathVariable UUID contractId,
                                                      @RequestBody ContractUpdateDTO request) {
        ContractDTO contract = contractService.updateContract(contractId, request);
        return ResponseEntity.ok(contract);
    }

    @GetMapping
    @Operation(summary = "Получить все договоры")
    public ResponseEntity<List<ContractDTO>> getAllContracts(@Parameter
                                                             @RequestParam(required = false) LocalDate startDate,
                                                             @Parameter
                                                             @RequestParam(required = false) LocalDate endDate
    ) {
        List<ContractDTO> contracts = contractService.getAllContracts(startDate, endDate);
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/{contractId}")
    @Operation(summary = "Получить детальный договор")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable UUID contractId) {
        ContractDTO contract = contractService.getDetailedContract(contractId);
        return ResponseEntity.ok(contract);
    }

    @GetMapping("/{contractId}/projects")
    @Operation(summary = "Получить проекты договора")
    public ResponseEntity<List<UUID>> getContractProjects(@PathVariable UUID contractId) {
        List<UUID> projectIds = contractService.getProjects(contractId);
        return ResponseEntity.ok(projectIds);
    }

    @DeleteMapping("/{contractId}")
    @Operation(summary = "Удалить договор")
    public ResponseEntity<Void> deleteContractById(@PathVariable UUID contractId) {
        contractService.deleteContract(contractId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{contractId}/exists")
    @Operation(summary = "Проверить существование договора")
    public ResponseEntity<Boolean> contractExists(@PathVariable UUID contractId) {
        return ResponseEntity.ok(contractService.contractExists(contractId));
    }

    @PutMapping("/{contractId}/projects")
    @Operation(summary = "Добавить проекты в договор")
    public ResponseEntity<ContractDTO> addProjectsToContract(@PathVariable UUID contractId,
                                                             @RequestBody List<UUID> projectIds
    ) {
        ContractDTO contract = contractService.addProjectsToContract(contractId, projectIds);
        return ResponseEntity.ok(contract);
    }

    @PatchMapping("/{contractId}/projects/remove")
    @Operation(summary = "Удалить проект из договора")
    public ResponseEntity<ContractDTO> removeProjectFromContract(@PathVariable UUID contractId,
                                                                 @RequestBody UUID projectId
    ) {
        ContractDTO contract = contractService.removeProjectFromContract(contractId, projectId);
        return ResponseEntity.ok(contract);
    }
}
