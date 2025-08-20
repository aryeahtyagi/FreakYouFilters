package org.atria.Configurations;

import jakarta.persistence.criteria.*;
import org.atria.Models.FilterObject;
import org.atria.Models.FilterValue;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpecificationBuilder<T> {

    private <Y> Path<Y> getPath(Root<T> root, String field) {
        String[] parts = field.split("\\.");
        Path<?> path = root;
        Join<?, ?> join = null;

        for(int i = 0; i < parts.length; ++i) {
            String part = parts[i];
            if (i == parts.length - 1) {
                path = path.get(part);
                return (Path<Y>) path;
            }

            if (join == null) {
                join = ((From)path).join(part, JoinType.LEFT);
            } else {
                join = join.join(part, JoinType.LEFT);
            }

            path = join;
        }

        return (Path<Y>) path;
    }

    public Specification<T> build(FilterObject filterObject) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();

            for(FilterValue filter : filterObject.getFilters()) {
                String value = filter.getValue();
                String operator = filter.getOperator();
                String field = filter.getField();
                Path<String> path = this.<String>getPath(root, field);
                switch (operator.toLowerCase()) {
                    case "eq":
                    case "=":
                        predicates.add(criteriaBuilder.equal(path, value));
                        break;
                    case ">":
                        predicates.add(criteriaBuilder.greaterThan(path, value));
                        break;
                    case "<":
                        predicates.add(criteriaBuilder.lessThan(path, value));
                        break;
                    case "like":
                        predicates.add(criteriaBuilder.like(path, "%" + value + "%"));
                        break;
                    case "!=":
                        predicates.add(criteriaBuilder.notEqual(path, value));
                        break;
                    default:
                        throw new UnsupportedOperationException("Operator not supported: " + operator);
                }
            }
            return criteriaBuilder.and((Predicate[])predicates.toArray(new Predicate[0]));
        };
    }
}
