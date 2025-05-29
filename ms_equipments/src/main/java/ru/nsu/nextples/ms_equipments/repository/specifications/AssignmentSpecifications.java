package ru.nsu.nextples.ms_equipments.repository.specifications;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_equipments.model.Assignment;
import ru.nsu.nextples.ms_equipments.model.Equipment;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AssignmentSpecifications {

    public static Specification<Assignment> hasActiveAssignment(UUID equipmentId, LocalDate targetDate) {
        return (root, query, cb) -> {
            Join<Assignment, Equipment> equipmentJoin = root.join("equipment");
            return cb.and(
                    cb.equal(equipmentJoin.get("id"), equipmentId),
                    cb.and(
                            cb.lessThanOrEqualTo(root.get("startDate"), targetDate),
                            cb.or(
                                    cb.greaterThanOrEqualTo(root.get("endDate"), targetDate),
                                    cb.isNull(root.get("endDate"))
                            )
                    )
            );
        };
    }

    public static Specification<Assignment> hasProjectIds(List<UUID> projectIds) {
        return (root, query, cb) -> {
            if (projectIds == null || projectIds.isEmpty()) {
                return cb.conjunction();
            }
            return root.get("projectId").in(projectIds);
        };
    }
}
