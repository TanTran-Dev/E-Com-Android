package com.teamadr.ecommerceapp.constants;

public enum UserType {
    SALESMAN("Salesman"),
    CUSTOMER("Customer");

    private String label;
    private String value;

    UserType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
