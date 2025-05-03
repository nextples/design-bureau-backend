package ru.nsu.nextples.ms_equipments.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_equipments.model.EquipmentType;

public class EquipmentTypeSpecifications {

    public static Specification<EquipmentType> isNotDeleted() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isDeleted"), false);
    }
}
