package com.teamadr.ecommerceapp.view.product_type;

import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;

import java.util.List;

public interface ProductTypeView {
    void refreshListProductType(List<ProductTypeDto> productTypeDtos);
}
