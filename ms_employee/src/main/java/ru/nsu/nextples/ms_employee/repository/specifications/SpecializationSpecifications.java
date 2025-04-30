package ru.nsu.nextples.ms_employee.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_employee.model.EngineerSpecialization;

import java.util.UUID;

public class SpecializationSpecifications {

    public static Specification<EngineerSpecialization> nameContains(String name) {
        return (root, query, cb) ->
        {
            if (name == null) return cb.conjunction();
            return cb.like(
                    cb.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<EngineerSpecialization> hasId(UUID id) {
        return (root, query, cb) ->
        {
            if (id == null) return cb.conjunction();
            return cb.equal(root.get("id"), id);
        };
    }
}
