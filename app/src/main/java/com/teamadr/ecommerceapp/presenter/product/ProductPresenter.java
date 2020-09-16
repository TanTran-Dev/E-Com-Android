package com.teamadr.ecommerceapp.presenter.product;

import com.teamadr.ecommerceapp.presenter.BasePresenter;

import java.util.List;

public interface ProductPresenter extends BasePresenter {
    void refreshProductDto(Integer productTypeId);
    void loadMoreProduct(Integer productTypeId);
    void setOrder(List<String> sortBy, List<String> sortType, Integer trademarkId);
    void setTrademarkId(Integer trademarkId);
}
