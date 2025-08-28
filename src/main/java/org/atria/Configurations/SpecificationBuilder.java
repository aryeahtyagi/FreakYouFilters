package org.atria.Configurations;

import jakarta.persistence.criteria.*;
import org.atria.Models.FilterObject;
import org.atria.Models.FilterValue;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public Specification<T> build(FilterObject filterObject,boolean caseSensitive) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();

            for(FilterValue filter : filterObject.getFilters()) {
                String value = filter.getValue();
                String operator = filter.getOperator();
                String field = filter.getField();
                String matchCase  = filter.getMatchCase();
                Path<String> path = this.<String>getPath(root, field);

                Expression<?> expression = path;
                // If case-insensitive, normalize both sides
                if (path.getJavaType().equals(String.class)){
                    if(matchCase == null){
                        if(!caseSensitive){
                            expression = criteriaBuilder.lower(path);
                            value = value.toLowerCase();
                        }
                    }
                    else if(matchCase.toLowerCase(Locale.ROOT).equals("no")
                        || matchCase.toLowerCase(Locale.ROOT).equals("false")
                    ) {
                        expression = criteriaBuilder.lower(path);
                        value = value.toLowerCase();
                    }
                    else if (matchCase.toLowerCase(Locale.ROOT).equals("yes")
                            || matchCase.toLowerCase(Locale.ROOT).equals("true"))
                    {

                    }
                    else{
                        throw new UnsupportedOperationException("matchCase value not supported: " + matchCase);
                    }
                }

                switch (operator.toLowerCase()) {
                    case "eq":
                    case "=":
                        predicates.add(criteriaBuilder.equal(expression, value));
                        break;
                    case ">":
                        predicates.add(criteriaBuilder.greaterThan(path, value));
                        break;
                    case "<":
                        predicates.add(criteriaBuilder.lessThan(path, value));
                        break;
                    case "like":
                        if (!path.getJavaType().equals(String.class)) {
                            throw new UnsupportedOperationException("LIKE operator only works on String fields");
                        }
                        predicates.add(criteriaBuilder.like((Expression<String>) expression, "%" + value + "%"));
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
    public Specification<T> build(FilterObject filterObject) {
        return build(filterObject,false);
    }


}
