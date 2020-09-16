package com.teamadr.ecommerceapp.presenter.shopping_cart;

import com.teamadr.ecommerceapp.presenter.BasePresenter;

import java.util.List;

public interface ShoppingCartPresenter extends BasePresenter {
    void refreshShoppingCartProducts();
    void loadMoreShoppingCartProducts();
    void deleteShoppingCartProducts(List<String> shoppingCartProductIDs);
    void setShoppingCartId(String shoppingCartId);
}
