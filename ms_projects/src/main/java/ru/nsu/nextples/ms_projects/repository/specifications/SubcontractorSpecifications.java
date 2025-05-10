package ru.nsu.nextples.ms_projects.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_projects.model.Subcontractor;

import java.util.UUID;

public class SubcontractorSpecifications {

    public static Specification<Subcontractor> notDeleted(UUID id) {
        return (root, query, cb) ->
                cb.and(
                        cb.equal(root.get("id"), id),
                        cb.isFalse(root.get("isDeleted"))
                );
    }

    public static Specification<Subcontractor> nameContains(String name) {
        return (root, query, cb) ->
        {
            if (name == null) return cb.conjunction();
            return cb.like(cb.lower(root.get("companyName")), "%" + name.toLowerCase() + "%");
        };
    }
}
