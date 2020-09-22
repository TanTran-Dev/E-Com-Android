package com.teamadr.ecommerceapp.model.request.order_product;

import com.google.gson.annotations.SerializedName;
import com.teamadr.ecommerceapp.model.request.shopping_cart.ShoppingCart;
import com.teamadr.ecommerceapp.model.response.salesman.SalesmanDto;
import com.teamadr.ecommerceapp.model.response.customer.CustomerDto;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;

public class OrderProductDto {
    @SerializedName("id")
    private String id;

    @SerializedName("deliveryAddress")
    private String deliveryAddress;

    @SerializedName("orderDate")
    private String orderDate;

    @SerializedName("deliveryDate")
    private String deliveryDate;

    @SerializedName("description")
    private String description;

    @SerializedName("product")
    private ProductDto product;

    @SerializedName("count")
    private int count;

    @SerializedName("customer")
    private CustomerDto customer;

    @SerializedName("salesmanDto")
    private SalesmanDto salesmanDto;

    @SerializedName("shoppingCart")
    private ShoppingCart shoppingCart;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public SalesmanDto getSalesmanDto() {
        return salesmanDto;
    }

    public void setSalesmanDto(SalesmanDto salesmanDto) {
        this.salesmanDto = salesmanDto;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
