package com.teamadr.ecommerceapp.view.order_product;

import com.teamadr.ecommerceapp.model.request.order_product.OrderProductDto;

import java.util.List;

public interface OrderProductView {
    void refreshOrders(List<OrderProductDto> orderProductDtos, int totalItems);
    void showRefreshingProgress();
    void hideRefreshingProgress();
    void addMoreOrderProducts(List<OrderProductDto> orderProductDtos);
    void disableLoadingMore(boolean disable);
    void enableLoadingMore(boolean enable);
    void showLoadingMore(boolean isShow);
    void showShimmerLoading();
    void hideShimmerLoading();
}
