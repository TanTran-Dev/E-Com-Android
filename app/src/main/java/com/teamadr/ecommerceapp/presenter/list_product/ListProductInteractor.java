package com.teamadr.ecommerceapp.presenter.list_product;

import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;
import com.teamadr.ecommerceapp.presenter.OnResponseListener;


import io.reactivex.functions.Consumer;

public interface ListProductInteractor extends BaseInteractor {
    void refreshProductTypes(int pageIndex, int pageSize,
                             OnResponseListener<ResponseBody<Page<ProductTypeDto>>, ResponseBody> listener);

    void refreshTrademarks(int pageIndex, int pageSize,
                           OnResponseListener<ResponseBody<Page<TrademarkDto>>, ResponseBody> listener);

    void refreshListProduct(Integer productTypeId, Integer trademarkId,
                            int pageIndex, int pageSize,
                            OnResponseListener<ResponseBody<Page<ProductDto>>, ResponseBody> listener);
}
