package ru.nsu.nextples.ms_projects.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_projects.model.Subcontractor;
import ru.nsu.nextples.ms_projects.model.SubcontractorWork;

import java.util.UUID;

public class SubcontractorWorkSpecifications {

    public static Specification<SubcontractorWork> hasSubcontractor(Subcontractor subcontractor) {
        return (root, query, cb) ->
                cb.equal(root.get("subcontractor"), subcontractor);
    }

    public static Specification<SubcontractorWork> isNotCompleted() {
        return (root, query, cb) ->
                cb.lessThan(root.get("progress"), 100);
    }

    public static Specification<SubcontractorWork> notDeleted(UUID workId) {
        return (root, query, cb) ->
                cb.and(
                        cb.equal(root.get("id"), workId),
                        cb.isFalse(root.get("deleted"))
                );

    }
}
