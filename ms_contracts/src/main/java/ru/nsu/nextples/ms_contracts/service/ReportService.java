package ru.nsu.nextples.ms_contracts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_contracts.dto.contract.EfficiencyDTO;
import ru.nsu.nextples.ms_contracts.repository.ContractRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportService {

    private final ContractService contractService;
    private final ContractRepository contractRepository;

    @Transactional(readOnly = true)
    public List<EfficiencyDTO> getContractEfficiency() {
        return contractRepository.findAll().stream()
                .map(contract -> {
                    BigDecimal totalCost = contractService.getContractCost(contract.getId());
                    LocalDate endDate = contract.getEndDate() != null ? contract.getEndDate() : LocalDate.now();
                    BigDecimal costPerDay = totalCost.divide(
                            BigDecimal.valueOf(ChronoUnit.DAYS.between(contract.getStartDate(), endDate)),
                            2,
                            RoundingMode.HALF_UP
                    );

                    EfficiencyDTO dto = new EfficiencyDTO();
                    dto.setContract(ContractService.mapToDTO(contract, false));
                    dto.setCostPerDay(costPerDay);

                    return dto;
                })
                .collect(Collectors.toList());
    }

}