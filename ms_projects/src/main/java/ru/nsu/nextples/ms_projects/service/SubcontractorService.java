package ru.nsu.nextples.ms_projects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_projects.dto.subcontractor.SubcontractorDTO;
import ru.nsu.nextples.ms_projects.model.Subcontractor;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubcontractorService {

    public static SubcontractorDTO mapToDTO(Subcontractor entity, boolean detailed) {
        SubcontractorDTO dto = new SubcontractorDTO();
        dto.setId(entity.getId());
        dto.setCompanyName(entity.getCompanyName());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        if (detailed) {
            dto.setWorks(entity.getWorks()
                    .stream()
                    .map(SubcontractorWorkService::mapToDTO)
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }
}
