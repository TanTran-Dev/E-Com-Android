package com.teamadr.ecommerceapp.presenter.order_product;

import com.teamadr.ecommerceapp.presenter.BasePresenter;

import java.util.List;


public interface OrderProductPresenter extends BasePresenter {
    void refreshOrderProducts();
    void loadMoreOrderProducts();
    void deleteOrderProducts(List<String> orderProductIds);
}
