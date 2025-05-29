package ru.nsu.nextples.ms_employee.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_employee.model.Employee;
import ru.nsu.nextples.ms_employee.model.EmployeeType;

import java.util.UUID;

public class EmployeeSpecifications {

    public static Specification<Employee> firstNameContains(String firstName) {
        return (root, query, cb) ->
        {
            if (firstName == null || firstName.isEmpty()) return cb.conjunction();
            String processedName = firstName.trim().toLowerCase();
            return cb.like(cb.lower(root.get("firstName")), "%" + processedName + "%");
        };
    }

    public static Specification<Employee> lastNameContains(String lastName) {
        return (root, query, cb) ->
        {
            if (lastName == null || lastName.isEmpty()) return cb.conjunction();
            String processedName = lastName.trim().toLowerCase();
            return cb.like(cb.lower(root.get("firstName")), "%" + processedName + "%");
        };
    }

    public static Specification<Employee> hasAgeFrom(Integer age) {
        return (root, query, cb) ->
        {
            if (age == null) return cb.conjunction();
            return cb.greaterThanOrEqualTo(root.get("age"), age);
        };
    }

    public static Specification<Employee> hasAgeTo(Integer age) {
        return (root, query, cb) ->
        {
            if (age == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("age"), age);
        };
    }

    public static Specification<Employee> hasEmployeeType(EmployeeType type) {
        return (root, query, cb) ->
        {
            if (type == null) return cb.conjunction();
            return cb.equal(root.get("employeeType"), type);
        };
    }

    public static Specification<Employee> hasPosition(UUID positionId) {
        return (root, query, cb) ->
        {
            if (positionId == null) return cb.conjunction();
            return cb.equal(root.join("position").get("id"), positionId);
        };
    }

    public static Specification<Employee> hasDepartment(UUID departmentId) {
        return (root, query, cb) ->
        {
            if (departmentId == null) return cb.conjunction();
            return cb.equal(root.join("department").get("id"), departmentId);
        };
    }
}
