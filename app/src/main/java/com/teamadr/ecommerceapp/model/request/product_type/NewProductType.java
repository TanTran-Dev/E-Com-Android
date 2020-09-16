package com.teamadr.ecommerceapp.model.request.product_type;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewProductType {
    @SerializedName("name")
    @Expose
    private String name;

    public NewProductType() {
    }

    public NewProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
