package org.atria.API;

import org.atria.Configurations.JsonParsingConfig;
import org.atria.Configurations.SpecificationBuilder;
import org.atria.Models.FilterObject;
import org.springframework.data.jpa.domain.Specification;

public class EasyFilters {

    public static <T> Specification<T> generateQuery(String json){
        FilterObject filterObject = JsonParsingConfig.getClass(json, FilterObject.class);
        SpecificationBuilder<T> specificationBuilder = new SpecificationBuilder<>();
        return specificationBuilder.build(filterObject);
    }

}
