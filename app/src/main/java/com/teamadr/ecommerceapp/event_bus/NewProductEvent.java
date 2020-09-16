package com.teamadr.ecommerceapp.event_bus;

import com.teamadr.ecommerceapp.model.request.product.NewProduct;

public class NewProductEvent {
    private NewProduct newProduct;

    public NewProductEvent(NewProduct newProduct) {
        this.newProduct = newProduct;
    }

    public NewProduct getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(NewProduct newProduct) {
        this.newProduct = newProduct;
    }
}
