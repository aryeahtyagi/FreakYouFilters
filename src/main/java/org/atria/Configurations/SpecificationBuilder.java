package org.atria.Configurations;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.atria.Models.FilterObject;
import org.atria.Models.FilterValue;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {

    public Specification<T> build(FilterObject filterObject){
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();

                for (FilterValue filter : filterObject.getFilters()) {
                    String value = filter.getValue();
                    String operator = filter.getOperator();
                    String field = filter.getField();

                    switch (operator.toLowerCase()){
                        case "eq":
                        case "=":
                            predicates.add(criteriaBuilder.equal(root.get(field),value));
                            break;
                        case ">":
                            predicates.add(criteriaBuilder.greaterThan(root.get(field), value));
                            break;
                        case "<":
                            predicates.add(criteriaBuilder.lessThan(root.get(field), value));
                            break;
                        case "like":
                            predicates.add(criteriaBuilder.like(root.get(field), "%" + value + "%"));
                            break;
                        case "!=":
                            predicates.add(criteriaBuilder.notEqual(root.get(field), value));
                            break;
                        default:
                            throw new UnsupportedOperationException("Operator not supported: " + operator);
                    }

                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }


}
