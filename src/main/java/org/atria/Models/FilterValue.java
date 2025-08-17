package org.atria.Models;

public class FilterValue {

    public String value;
    public String operator;
    public String field;

    public FilterValue() {
    }

    public FilterValue(String value, String operator, String field) {
        this.value = value;
        this.operator = operator;
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
