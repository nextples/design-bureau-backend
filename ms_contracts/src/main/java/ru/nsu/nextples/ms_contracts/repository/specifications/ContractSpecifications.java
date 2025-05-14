package ru.nsu.nextples.ms_contracts.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_contracts.model.Contract;

import java.time.LocalDate;
import java.util.UUID;

public class ContractSpecifications {

    public static Specification<Contract> notDeleted(UUID id) {
        return (root, query, cb) ->
                cb.and(
                        cb.equal(root.get("id"), id),
                        cb.isFalse(root.get("is_deleted"))
                );
    }

    public static Specification<Contract> hasDateUntil(LocalDate end) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("endDate"), end);
    }

    public static Specification<Contract> hasDateFrom(LocalDate start) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("startDate"), start);
    }
}
