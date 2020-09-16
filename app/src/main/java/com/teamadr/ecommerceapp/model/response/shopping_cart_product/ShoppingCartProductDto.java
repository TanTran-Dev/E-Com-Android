package com.teamadr.ecommerceapp.model.response.shopping_cart_product;

import com.google.gson.annotations.SerializedName;
import com.teamadr.ecommerceapp.model.request.shopping_cart.ShoppingCart;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;

public class ShoppingCartProductDto {
    @SerializedName("id")
    private String id;

    @SerializedName("productDto")
    private ProductDto productDto;

    @SerializedName("shoppingCartDto")
    private ShoppingCart shoppingCartDto;

    @SerializedName("count")
    private int count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public ShoppingCart getShoppingCartDto() {
        return shoppingCartDto;
    }

    public void setShoppingCartDto(ShoppingCart shoppingCartDto) {
        this.shoppingCartDto = shoppingCartDto;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
