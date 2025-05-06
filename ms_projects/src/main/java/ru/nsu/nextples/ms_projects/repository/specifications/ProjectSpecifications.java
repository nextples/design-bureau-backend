package ru.nsu.nextples.ms_projects.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_projects.model.Project;

import java.util.UUID;

public class ProjectSpecifications {

    public static Specification<Project> notDeleted(UUID id) {
        return (root, query, cb) ->
                cb.and(
                        cb.equal(root.get("id"), id),
                        cb.isFalse(root.get("deleted"))
                );
    }
}