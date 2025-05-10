package ru.nsu.nextples.ms_projects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_projects.dto.subcontractor.SubcontractorCreateDTO;
import ru.nsu.nextples.ms_projects.dto.subcontractor.SubcontractorDTO;
import ru.nsu.nextples.ms_projects.dto.subcontractor.SubcontractorUpdateDTO;
import ru.nsu.nextples.ms_projects.exception.DeleteConflictException;
import ru.nsu.nextples.ms_projects.exception.ObjectNotFoundException;
import ru.nsu.nextples.ms_projects.model.Project;
import ru.nsu.nextples.ms_projects.model.Subcontractor;
import ru.nsu.nextples.ms_projects.model.SubcontractorWork;
import ru.nsu.nextples.ms_projects.repository.ProjectRepository;
import ru.nsu.nextples.ms_projects.repository.SubcontractorRepository;
import ru.nsu.nextples.ms_projects.repository.SubcontractorWorkRepository;
import ru.nsu.nextples.ms_projects.repository.specifications.SubcontractorSpecifications;
import ru.nsu.nextples.ms_projects.repository.specifications.SubcontractorWorkSpecifications;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubcontractorService {

    private final SubcontractorRepository subcontractorRepository;
    private final SubcontractorWorkRepository subcontractorWorkRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public SubcontractorDTO createSubcontractor(SubcontractorCreateDTO request) {
        Subcontractor subcontractor = new Subcontractor();
        subcontractor.setCompanyName(request.getCompanyName());
        subcontractor.setEmail(request.getEmail());
        subcontractor.setPhoneNumber(request.getPhoneNumber());
        Subcontractor savedSubcontractor = subcontractorRepository.save(subcontractor);
        return mapToDTO(savedSubcontractor, true);
    }

    @Transactional
    public SubcontractorDTO updateSubcontractor(UUID subcontractorId, SubcontractorUpdateDTO request) {
        Subcontractor subcontractor = subcontractorRepository.findOne(SubcontractorSpecifications.notDeleted(subcontractorId))
                .orElseThrow(() -> new ObjectNotFoundException("Subcontractor", subcontractorId));

        if (request.getCompanyName() != null) {
            subcontractor.setCompanyName(request.getCompanyName());
        }
        if (request.getEmail() != null) {
            subcontractor.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            subcontractor.setPhoneNumber(request.getPhoneNumber());
        }

        Subcontractor savedSubcontractor = subcontractorRepository.save(subcontractor);
        return mapToDTO(savedSubcontractor, true);
    }

    @Transactional(readOnly = true)
    public List<SubcontractorDTO> getAllSubcontractors(String name) {
        Specification<Subcontractor> filterSpec = Specification.where(null);
        if (name != null) {
            filterSpec = filterSpec.and(SubcontractorSpecifications.nameContains(name));
        }
        List<Subcontractor> subcontractors = subcontractorRepository.findAll(filterSpec);
        return subcontractors
                .stream()
                .map((subcontractor) -> SubcontractorService.mapToDTO(subcontractor, false))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubcontractorDTO getSubcontractor(UUID subcontractorId) {
        Subcontractor subcontractor = subcontractorRepository.findOne(SubcontractorSpecifications.notDeleted(subcontractorId))
                .orElseThrow(() -> new ObjectNotFoundException("Subcontractor", subcontractorId));

        return mapToDTO(subcontractor, true);
    }

    @Transactional
    public void deleteSubcontractor(UUID id) {
        Subcontractor subcontractor = subcontractorRepository.findOne(SubcontractorSpecifications.notDeleted(id))
                .orElseThrow(() -> new ObjectNotFoundException("Subcontractor", id));


        boolean hasIncompleteWorks = subcontractorWorkRepository.exists(
                SubcontractorWorkSpecifications.hasSubcontractor(subcontractor)
                        .and(SubcontractorWorkSpecifications.isNotCompleted())
        );
        if (hasIncompleteWorks) {
            throw new DeleteConflictException("Subcontractor", id);
        }

        List<SubcontractorWork> works = subcontractorWorkRepository.findAll(
                SubcontractorWorkSpecifications.hasSubcontractor(subcontractor)
        );
        works.forEach(work -> work.setIsDeleted(true));
        subcontractorWorkRepository.saveAll(works);

        subcontractor.setIsDeleted(true);
        subcontractorRepository.save(subcontractor);
    }

    @Transactional
    public void updateSubcontractorProgress(UUID workId, int newProgress) {
        SubcontractorWork work = subcontractorWorkRepository.findById(workId)
                .orElseThrow(() -> new ObjectNotFoundException("Subcontractor work", workId));

        work.setProgress(newProgress);
        subcontractorWorkRepository.save(work);

        Project project = work.getProject();
        ProjectService.recalculateTotalProgress(project);
        projectRepository.save(project);
    }

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
