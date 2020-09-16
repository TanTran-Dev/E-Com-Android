package com.teamadr.ecommerceapp.presenter.order_product;

import com.teamadr.ecommerceapp.model.request.order_product.OrderProductDto;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface OrderProductInteractor extends BaseInteractor {

    void getListOrderProduct(List<String> sortBy, List<String> sortType,
                             int pageIndex, int pageSize,
                             Consumer<ResponseBody<Page<OrderProductDto>>> onSuccess,
                             Consumer<Throwable> onError);
    void deleteListOrderProducts(List<String> orderProductIds,
                                       Consumer<ResponseBody> onSuccess,
                                       Consumer<Throwable> onError);
}
