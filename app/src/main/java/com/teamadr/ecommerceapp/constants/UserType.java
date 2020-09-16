package com.teamadr.ecommerceapp.constants;

public enum UserType {
    ADMIN("Admin"),
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
