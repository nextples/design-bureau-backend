package ru.nsu.nextples.ms_equipments.repository.specifications;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_equipments.model.Assignment;
import ru.nsu.nextples.ms_equipments.model.Equipment;

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

    public static Specification<Equipment> hasTypeId(UUID typeId) {
        return (root, query, cb) ->
        {
            if (typeId == null) return cb.conjunction();
            return cb.equal(root.get("type").get("id"), typeId);
        };
    }

    public static Specification<Equipment> hasActiveProjects() {
        return (root, query, cb) -> {
            assert query != null;
            Subquery<Assignment> subquery = query.subquery(Assignment.class);
            Root<Assignment> assignmentRoot = subquery.from(Assignment.class);

            subquery.select(assignmentRoot)
                    .where(
                            cb.equal(assignmentRoot.get("equipmentId"), root.get("id")),
                            cb.isNull(assignmentRoot.get("endDate"))
                    );

            return cb.exists(subquery);
        };
    }

    public static Specification<Equipment> hasNoActiveProjects() {
        return Specification.not(hasActiveProjects());
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
}
