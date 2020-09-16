package com.teamadr.ecommerceapp.view.product;

import com.teamadr.ecommerceapp.model.response.product.ProductDto;

import java.util.List;

public interface ProductView {
    void refreshProductDto(List<ProductDto> productDtos, int totalItems);
    void addMoreProducts(List<ProductDto> productDtos);

    void showRefreshingProgress();
    void hideRefreshingProgress();
    void showLoadingProgress();

    void hideLoadingProgress();

    void showLoadingMore(boolean isShow);
    void disableLoadingMore(boolean disable);
    void enableLoadingMore(boolean enable);
    void showShimmerLoading();
    void hideShimmerLoading();
}
