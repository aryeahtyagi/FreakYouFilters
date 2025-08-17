package org.atria.Models;

import java.util.List;

public class FilterObject {

    public List<FilterValue> filters;

    public FilterObject() {
    }

    public FilterObject(List<FilterValue> filters) {
        this.filters = filters;
    }

    public List<FilterValue> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterValue> filters) {
        this.filters = filters;
    }
}
