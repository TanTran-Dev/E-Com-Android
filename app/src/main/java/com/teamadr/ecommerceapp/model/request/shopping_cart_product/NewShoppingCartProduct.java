package com.teamadr.ecommerceapp.model.request.shopping_cart_product;

import com.google.gson.annotations.SerializedName;

public class NewShoppingCartProduct {
    @SerializedName("count")
    private int count;

    @SerializedName("productId")
    private int productId;

    @SerializedName("shoppingCartId")
    private String shoppingCartId;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }
}
