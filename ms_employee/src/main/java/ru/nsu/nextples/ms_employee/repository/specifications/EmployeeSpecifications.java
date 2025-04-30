package ru.nsu.nextples.ms_employee.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.nsu.nextples.ms_employee.model.Employee;
import ru.nsu.nextples.ms_employee.model.EmployeeType;

import java.util.UUID;

public class EmployeeSpecifications {

    public static Specification<Employee> firstNameContains(String firstName) {
        return (root, query, cb) ->
        {
            if (firstName == null) return null;
            return cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
        };
    }

    public static Specification<Employee> lastNameContains(String name) {
        return (root, query, cb) ->
        {
            if (name == null) return null;
            return cb.like(cb.lower(root.get("lastName")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Employee> hasAgeFrom(Integer age) {
        return (root, query, cb) ->
        {
            if (age == null) return null;
            return cb.greaterThanOrEqualTo(root.get("age"), age);
        };
    }

    public static Specification<Employee> hasAgeTo(Integer age) {
        return (root, query, cb) ->
        {
            if (age == null) return null;
            return cb.lessThanOrEqualTo(root.get("age"), age);
        };
    }

    public static Specification<Employee> hasEmployeeType(EmployeeType type) {
        return (root, query, cb) ->
        {
            if (type == null) return null;
            return cb.equal(root.get("employeeType"), type);
        };
    }

    public static Specification<Employee> hasPosition(UUID positionId) {
        return (root, query, cb) ->
        {
            if (positionId == null) return null;
            return cb.equal(root.join("position").get("id"), positionId);
        };
    }

    public static Specification<Employee> hasDepartment(UUID departmentId) {
        return (root, query, cb) ->
        {
            if (departmentId == null) return null;
            return cb.equal(root.join("department").get("id"), departmentId);
        };
    }
}
