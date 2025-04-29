package ru.nsu.nextples.ms_employee.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_employee.model.Department;

import java.util.UUID;

public class DepartmentSpecifications {

    public static Specification<Department> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(
                    cb.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Department> hasHead(UUID headId) {
        return (root, query, cb) ->
        {
            if (headId == null) return cb.conjunction();
            return cb.equal(root.get("head").get("id"), headId);
        };
    }
}
