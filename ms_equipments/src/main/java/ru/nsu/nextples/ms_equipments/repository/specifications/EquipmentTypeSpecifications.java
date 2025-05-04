package ru.nsu.nextples.ms_equipments.repository.specifications;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_equipments.model.Equipment;
import ru.nsu.nextples.ms_equipments.model.EquipmentType;

import java.util.UUID;

public class EquipmentTypeSpecifications {

    public static Specification<EquipmentType> canBeSafelyDeleted(UUID id) {
        return (root, query, cb) -> {
            Predicate hasId = cb.equal(root.get("id"), id);
            Predicate notDeleted = cb.isFalse(root.get("isDeleted"));

            assert query != null;
            Subquery<UUID> equipmentSubquery = query.subquery(UUID.class);
            Root<Equipment> equipmentRoot = equipmentSubquery.from(Equipment.class);
            equipmentSubquery.select(equipmentRoot.get("type").get("id"))
                    .where(
                            cb.equal(equipmentRoot.get("type"), root),
                            cb.isFalse(equipmentRoot.get("isDeleted"))
                    );

            Predicate noActiveEquipment = cb.not(cb.exists(equipmentSubquery));

            return cb.and(hasId, notDeleted, noActiveEquipment);
        };
    }

    public static Specification<EquipmentType> notDeleted() {
        return (root, query, cb) ->
                cb.isFalse(root.get("isDeleted"));
    }

    public static Specification<EquipmentType> notDeleted(UUID id) {
        return (root, query, cb) ->
                cb.and(
                        cb.isFalse(root.get("isDeleted")),
                        cb.equal(root.get("id"), id)
                );
    }
}
