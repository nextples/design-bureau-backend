package ru.nsu.nextples.ms_employee.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_employee.model.Position;
import ru.nsu.nextples.ms_employee.model.EmployeeType;

public class PositionSpecifications {

    public static Specification<Position> hasEmployeeType(EmployeeType type) {
        return ((root, query, cb) ->
        {
            if (type == null) return cb.conjunction();
            return cb.equal(root.get("employeeType"), type);
        }
        );
    }

    public static Specification<Position> nameContains(String name) {
        return (root, query, criteriaBuilder) ->
        {
            if (name == null) return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }
}
