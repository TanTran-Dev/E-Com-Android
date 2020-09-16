package com.teamadr.ecommerceapp.constants;

public enum SortType {
    ASCENDING("Tăng dần", "asc"),
    DESCENDING("Giảm dần", "desc"),
    NULL("Không", "");

    private String label;
    private String value;

    SortType(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }
}
