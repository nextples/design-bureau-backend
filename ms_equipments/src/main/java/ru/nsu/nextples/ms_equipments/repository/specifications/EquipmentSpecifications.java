package ru.nsu.nextples.ms_equipments.repository.specifications;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_equipments.model.*;

import java.util.UUID;

public class EquipmentSpecifications {

    public static Specification<Equipment> hasId(UUID id) {
        return (root, query, cb) ->
        {
            if (id == null) return cb.conjunction();
            return cb.equal(root.get("id"), id);
        };
    }

    public static Specification<Equipment> nameContains(String name) {
        return (root, query, cb) ->
        {
            if (name == null) return cb.conjunction();
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Equipment> hasDepartmentId(UUID departmentId) {
        return (root, query, cb) ->
        {
            if (departmentId == null) return cb.conjunction();
            return cb.equal(root.get("currentDepartmentId"), departmentId);
        };
    }

    public static Specification<Equipment> hasProjectId(UUID projectId) {
        return (root, query, cb) ->
        {
            if (projectId == null) return cb.conjunction();
            return cb.equal(root.get("currentProjectId"), projectId);
        };
    }

    public static Specification<Equipment> hasTypeId(UUID typeId) {
        return (root, query, cb) ->
        {
            if (typeId == null) return cb.conjunction();
            return cb.equal(root.get("typeId"), typeId);
        };
    }

    public static Specification<Equipment> hasActiveProjects() {
        return (root, query, cb) -> {
            assert query != null;
            Subquery<ProjectAssignment> subquery = query.subquery(ProjectAssignment.class);
            Root<ProjectAssignment> assignmentRoot = subquery.from(ProjectAssignment.class);

            subquery.select(assignmentRoot)
                    .where(
                            cb.equal(assignmentRoot.get("equipmentId"), root.get("id")),
                            cb.isNull(assignmentRoot.get("endDate"))
                    );

            return cb.exists(subquery);
        };
    }

    public static Specification<Equipment> hasActiveDepartments() {
        return (root, query, cb) -> {
            assert query != null;
            Subquery<DepartmentAssignment> subquery = query.subquery(DepartmentAssignment.class);
            Root<DepartmentAssignment> AssignmentRoot = subquery.from(DepartmentAssignment.class);

            subquery.select(AssignmentRoot)
                    .where(
                            cb.equal(AssignmentRoot.get("equipmentId"), root.get("id")),
                            cb.isNull(AssignmentRoot.get("endDate"))
                    );

            return cb.exists(subquery);
        };
    }

    public static Specification<Equipment> hasNoActiveDependencies() {
        return Specification.not(hasActiveProjects().or(hasActiveDepartments()));
    }

    public static Specification<Equipment> notDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("isDeleted"));
    }

    public static Specification<Equipment> notDeleted(UUID id) {
        return (root, query, cb) ->
                cb.and(
                        cb.isFalse(root.get("isDeleted")),
                        cb.equal(root.get("id"), id)
                );
    }

    public static Specification<Equipment> isDeleted() {
        return (root, query, cb) -> cb.isTrue(root.get("isDeleted"));
    }

    public static Specification<Equipment> isSharedEquipment() {
        return (root, query, cb) -> cb.isTrue(root.get("isShared"));
    }

    public static Specification<Equipment> isAvailableForProjectAssignment(UUID id) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("id"), id),
                cb.isNull(root.get("currentProjectId")),
                cb.isFalse(root.get("onMaintenance")),
                cb.isFalse(root.get("isDeleted"))
        );
    }

    public static Specification<Equipment> isAvailableForDepartmentAssignment(UUID id) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("id"), id),
                cb.isNull(root.get("currentProjectId")),
                cb.isFalse(root.get("onMaintenance")),
                cb.isFalse(root.get("isDeleted"))
        );
    }
}
