package com.teamadr.ecommerceapp.model.request.order_product;

import com.google.gson.annotations.SerializedName;

public class NewOrderProduct {
    @SerializedName("deliveryAddress")
    private String deliveryAddress;

    @SerializedName("orderDate")
    private String orderDate;

    @SerializedName("deliveryDate")
    private String deliveryDate;

    @SerializedName("description")
    private String description;

    @SerializedName("productId")
    private Integer productId;

    @SerializedName("count")
    private Integer count;

    @SerializedName("customerId")
    private String customerId;

    @SerializedName("adminId")
    private String adminId;

    @SerializedName("shoppingCartId")
    private String shoppingCartId;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
