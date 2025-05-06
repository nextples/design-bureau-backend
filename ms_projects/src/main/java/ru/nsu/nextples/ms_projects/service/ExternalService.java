package ru.nsu.nextples.ms_projects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_projects.client.ContractServiceClient;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExternalService {

    private final ContractServiceClient contractService;

}