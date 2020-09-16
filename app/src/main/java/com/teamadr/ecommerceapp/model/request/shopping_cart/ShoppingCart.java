package com.teamadr.ecommerceapp.model.request.shopping_cart;

import com.google.gson.annotations.SerializedName;
import com.teamadr.ecommerceapp.model.response.customer.CustomerDto;

public class ShoppingCart {
    @SerializedName("id")
    private String id;
    @SerializedName("customerDto")
    private CustomerDto customerDto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }
}
