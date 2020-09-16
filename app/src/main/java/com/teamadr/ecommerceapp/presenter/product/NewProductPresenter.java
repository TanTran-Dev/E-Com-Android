package com.teamadr.ecommerceapp.presenter.product;

import com.teamadr.ecommerceapp.model.request.product.NewProduct;

public interface NewProductPresenter {
    void addProduct(NewProduct newProduct);
    void setTrademarkId(Integer trademarkId);
    void refreshTrademark();
    void refreshProductType();
}
