package com.teamadr.ecommerceapp.presenter.product_type;

import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import io.reactivex.functions.Consumer;

public interface ProductTypeInteractor extends BaseInteractor {
    void refreshProductTypes(String sortBy, String sortType, int pageIndex, int pageSize,
                             Consumer<ResponseBody<Page<ProductTypeDto>>> onSuccess,
                             Consumer<Throwable> onError);
}
