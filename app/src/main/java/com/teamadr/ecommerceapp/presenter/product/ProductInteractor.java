package com.teamadr.ecommerceapp.presenter.product;

import com.teamadr.ecommerceapp.model.request.product.NewProduct;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface ProductInteractor extends BaseInteractor {
    void refreshListProduct(Integer productTypeId, Integer trademarkId, List<String> sortBy, List<String> sortType,
                            int pageIndex, int pageSize,
                            Consumer<ResponseBody<Page<ProductDto>>> onSuccess,
                            Consumer<Throwable> onError);

    void addNewProduct(NewProduct newProduct,
                       Consumer<ResponseBody> onSuccess,
                       Consumer<Throwable> onError);

    void refreshTrademarks(List<String> sortBy, List<String> sortType, int pageIndex, int pageSize,
                           Consumer<ResponseBody<Page<TrademarkDto>>> onSuccess,
                           Consumer<Throwable> onError);

    void refreshProductTypes(String sortBy, String sortType, int pageIndex, int pageSize,
                             Consumer<ResponseBody<Page<ProductTypeDto>>> onSuccess,
                             Consumer<Throwable> onError);

    void deleteProducts(List<Integer> productIds,
                        Consumer<ResponseBody> onSuccess,
                        Consumer<Throwable> onError);

    void getProductByAdmin(List<String> sortBy, List<String> sortType,
                           int pageIndex, int pageSize,
                           Consumer<ResponseBody<Page<ProductDto>>> onSuccess,
                           Consumer<Throwable> onError);
}
