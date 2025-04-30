package ru.nsu.nextples.ms_employee.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_employee.model.Laboratory;

import java.util.UUID;

public class LaboratorySpecifications {

    public static Specification<Laboratory> nameContains(String name) {
        return (root, query, cb) ->
        {
            if (name == null) return cb.conjunction();
            return cb.like(
                    cb.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Laboratory> hasId(UUID id) {
        return (root, query, cb) ->
        {
            if (id == null) return cb.conjunction();
            return cb.equal(root.get("id"), id);
        };
    }
}
