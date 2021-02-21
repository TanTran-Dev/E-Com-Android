package com.teamadr.ecommerceapp.presenter.list_product;

import com.teamadr.ecommerceapp.presenter.BasePresenter;

public interface ListProductPresenter extends BasePresenter {
    void refreshPage();
    void loadMoreProducts();
}
