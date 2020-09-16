package com.teamadr.ecommerceapp.view.shopping_cart;

import com.teamadr.ecommerceapp.model.response.shopping_cart_product.ShoppingCartProductDto;

import java.util.List;

public interface ShoppingCartView {
    void refreshShoppingCartProduct(List<ShoppingCartProductDto> list, int totalItems);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void addMoreShoppingCartProduct(List<ShoppingCartProductDto> shoppingCartProductDtos);

    void disableLoadingMore(boolean disable);
    void enableLoadingMore(boolean enable);
    void showLoadingMore(boolean isShow);
    void showShimmerLoading();
    void hideShimmerLoading();
}
