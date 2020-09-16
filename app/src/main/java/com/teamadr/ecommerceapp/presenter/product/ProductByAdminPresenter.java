package com.teamadr.ecommerceapp.presenter.product;

import com.teamadr.ecommerceapp.presenter.BasePresenter;

import java.util.List;

public interface ProductByAdminPresenter extends BasePresenter {
    void refreshPageProductByAdmin();
    void deleteProducts(List<Integer> productIds);
    void loadMoreProduct();
}
