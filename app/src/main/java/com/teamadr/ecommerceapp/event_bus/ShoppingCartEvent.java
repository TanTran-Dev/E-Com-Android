package com.teamadr.ecommerceapp.event_bus;

public class ShoppingCartEvent {
    private String shoppingCartId;

    public ShoppingCartEvent(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }
}
