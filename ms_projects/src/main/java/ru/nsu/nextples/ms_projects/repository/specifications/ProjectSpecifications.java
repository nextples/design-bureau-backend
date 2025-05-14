package ru.nsu.nextples.ms_projects.repository.specifications;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_projects.model.Project;
import ru.nsu.nextples.ms_projects.model.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ProjectSpecifications {

    public static Specification<Project> notDeleted(UUID id) {
        return (root, query, cb) ->
                cb.and(
                        cb.equal(root.get("id"), id),
                        cb.isFalse(root.get("isDeleted"))
                );
    }

    public static Specification<Project> hasStatus(ProjectStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Project> hasDateUntil(LocalDate end) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("endDate"), end);
    }

    public static Specification<Project> hasDateFrom(LocalDate start) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("startDate"), start);
    }

    public static Specification<Project> hasCostFrom(BigDecimal min) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("cost"), min);
    }

    public static Specification<Project> hasCostTo(BigDecimal msx) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("cost"), msx);
    }

    public static Specification<Project> hasIdsIn(List<UUID> ids) {
        return (root, query, cb) -> {
            if (ids == null || ids.isEmpty()) return cb.conjunction();
            return root.get("id").in(ids);
        };
    }

    public static Specification<Project> selectIdAndCost() {
        return (root, query, cb) -> {
            assert query != null;
            query.multiselect(root.get("id"), root.get("cost"));
            return cb.conjunction();
        };
    }
}