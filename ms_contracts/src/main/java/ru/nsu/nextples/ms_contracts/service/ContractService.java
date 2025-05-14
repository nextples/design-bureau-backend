package ru.nsu.nextples.ms_contracts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_contracts.dto.contract.ContractCreateDTO;
import ru.nsu.nextples.ms_contracts.dto.contract.ContractDTO;
import ru.nsu.nextples.ms_contracts.dto.contract.ContractUpdateDTO;
import ru.nsu.nextples.ms_contracts.exception.DeleteConflictException;
import ru.nsu.nextples.ms_contracts.exception.ObjectNotFoundException;
import ru.nsu.nextples.ms_contracts.model.Contract;
import ru.nsu.nextples.ms_contracts.repository.ContractRepository;
import ru.nsu.nextples.ms_contracts.repository.specifications.ContractSpecifications;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContractService {

    private final ExternalService externalService;
    private final ContractRepository contractRepository;

    @Transactional
    public ContractDTO createContract(ContractCreateDTO request) {
        externalService.checkEmployeeExists(request.getManagerId());

        Contract contract = new Contract();
        contract.setName(request.getName());
        contract.setStartDate(LocalDate.now());
        contract.setManagerId(request.getManagerId());

        Contract savedContract = contractRepository.save(contract);
        return mapToDTO(savedContract, true);
    }

    @Transactional
    public ContractDTO updateContract(UUID contractId, ContractUpdateDTO request) {
        Contract contract = contractRepository.findOne(ContractSpecifications.notDeleted(contractId))
                .orElseThrow(() -> new ObjectNotFoundException("Contract", contractId));

        externalService.checkEmployeeExists(contract.getManagerId());

        if (request.getName() != null) {
            contract.setName(request.getName());
        }
        if (request.getManagerId() != null) {
            contract.setManagerId(request.getManagerId());
        }

        Contract savedContract = contractRepository.save(contract);
        return mapToDTO(savedContract, true);
    }

    @Transactional(readOnly = true)
    public ContractDTO getDetailedContract(UUID contractId) {
        Contract contract = contractRepository.findOne(ContractSpecifications.notDeleted(contractId))
                .orElseThrow(() -> new ObjectNotFoundException("Contract", contractId));

        return mapToDTO(contract, true);
    }

    @Transactional(readOnly = true)
    public List<ContractDTO> getAllContracts(LocalDate startDate, LocalDate endDate) {
        Specification<Contract> filterSpec = Specification.where(null);
        if (startDate != null) {
            filterSpec = filterSpec.and(ContractSpecifications.hasDateFrom(startDate));
        }
        if (endDate != null) {
            filterSpec = filterSpec.and(ContractSpecifications.hasDateUntil(endDate));
        }

        List<Contract> contracts = contractRepository.findAll(filterSpec);

        return contracts
                .stream()
                .map(contract -> ContractService.mapToDTO(contract, false))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Boolean contractExists(UUID contractId) {
        return contractRepository.exists(ContractSpecifications.notDeleted(contractId));
    }

    @Transactional(readOnly = true)
    public List<UUID> getProjects(UUID contractId) {
        Contract contract = contractRepository.findOne(ContractSpecifications.notDeleted(contractId))
                .orElseThrow(() -> new ObjectNotFoundException("Contract", contractId));

        return contract.getProjectIds();
    }

    @Transactional
    public void deleteContract(UUID id) {
        Contract contract = contractRepository.findOne(ContractSpecifications.notDeleted(id))
                .orElseThrow(() -> new ObjectNotFoundException("Contract", id));

        if (!contract.getProjectIds().isEmpty()) {
            throw new DeleteConflictException("Contract", id);
        }
        contract.setIsDeleted(true);

        contractRepository.save(contract);
    }

    @Transactional
    public ContractDTO addProjectsToContract(UUID contractId, List<UUID> projectIds) {
        Contract contract = contractRepository.findOne(ContractSpecifications.notDeleted(contractId))
                .orElseThrow(() -> new ObjectNotFoundException("Contract", contractId));

        externalService.checkProjectsExists(projectIds);

        contract.getProjectIds().addAll(projectIds);
        Contract savedContract = contractRepository.save(contract);
        return mapToDTO(savedContract, true);
    }

    @Transactional
    public ContractDTO removeProjectFromContract(UUID contractId, UUID projectId) {
        Contract contract = contractRepository.findOne(ContractSpecifications.notDeleted(contractId))
                .orElseThrow(() -> new ObjectNotFoundException("Contract", contractId));

        if (contract.getProjectIds().remove(projectId)) {
            Contract savedContract = contractRepository.save(contract);
            return mapToDTO(savedContract, true);
        }
        return mapToDTO(contract, true);
    }

    @Transactional(readOnly = true)
    public BigDecimal getContractCost(UUID contractId) {
        Contract contract = contractRepository.findOne(ContractSpecifications.notDeleted(contractId))
                .orElseThrow(() -> new ObjectNotFoundException("Contract", contractId));

        Map<UUID, BigDecimal> projectCostMap = externalService.getProjectsCosts(contract.getProjectIds());

        BigDecimal totalCost = BigDecimal.ZERO;
        for (Map.Entry<UUID, BigDecimal> entry : projectCostMap.entrySet()) {
            totalCost = totalCost.add(entry.getValue());
        }
        return totalCost;
    }

    public static ContractDTO mapToDTO(Contract entity, boolean detailed) {
        ContractDTO dto = new ContractDTO();
        dto.setContractId(entity.getId());
        dto.setName(entity.getName());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setManagerId(entity.getManagerId());
        if (detailed) {
            dto.setProjectIds(entity.getProjectIds());
        }
        return dto;
    }
}
