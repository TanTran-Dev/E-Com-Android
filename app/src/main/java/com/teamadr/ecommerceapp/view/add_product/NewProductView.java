package com.teamadr.ecommerceapp.view.add_product;

import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;

import java.util.List;

public interface NewProductView {
    void refreshTrademark(List<TrademarkDto> list);
    void refreshListProductType(List<ProductTypeDto> productTypeDtos);
    void showLoadingProgress();
    void hideLoadingProgress();
}
