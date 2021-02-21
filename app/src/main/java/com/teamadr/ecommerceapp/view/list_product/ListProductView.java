package com.teamadr.ecommerceapp.view.list_product;

import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;

import java.util.List;

public interface ListProductView {
    void refreshListProducts(List<ProductDto> products);
    void refreshListProductType(List<ProductTypeDto> productTypes);
    void refreshListTrademark(List<TrademarkDto> trademarks);
    void addProducts(List<ProductDto> products);

    void showLoadingProgress();
    void hideLoadingProgress();

    void showRefreshingProgress();

    void hideRefreshingProgress();

    void showLoadMoreProgress();
    void hideLoadMoreProgress();

    void enableLoadMore(boolean enable);
    void enableRefreshing(boolean enable);
}
