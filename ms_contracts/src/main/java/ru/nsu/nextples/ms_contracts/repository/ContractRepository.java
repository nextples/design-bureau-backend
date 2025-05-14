package ru.nsu.nextples.ms_contracts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.nsu.nextples.ms_contracts.model.Contract;

import java.util.UUID;

@Repository
public interface ContractRepository
        extends JpaRepository<Contract, UUID>, JpaSpecificationExecutor<Contract> {


}