package com.teamadr.ecommerceapp.presenter.product_type;

import android.content.Context;
import android.util.Log;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.view.product_type.ProductTypeView;

import java.util.List;


public class ProductTypePresenterImpl implements ProductTypePresenter {
    private Context context;
    private ProductTypeView view;
    private ProductTypeInteractor interactor;

    public ProductTypePresenterImpl(Context context, ProductTypeView view) {
        this.context = context;
        this.view = view;
        this.interactor = new ProductTypeInteractorImpl(context);
    }

    @Override
    public void refreshProductType() {
        interactor.refreshProductTypes(null, null, 0, 50,
                productTypeDtoResponseBody -> {
                    Log.i("LIST_PRODUCT_TYPES", "accept: " + productTypeDtoResponseBody.getData());
                    List<ProductTypeDto> productTypeDtos = productTypeDtoResponseBody.getData().getItems();
                    view.refreshListProductType(productTypeDtos);
                },
                throwable -> {
                    Log.d("ERROR", "accept: " + throwable.toString());
                    throwable.printStackTrace();
                });
    }

    @Override
    public void onViewDestroy() {
        interactor.onViewDestroy();
    }
}
