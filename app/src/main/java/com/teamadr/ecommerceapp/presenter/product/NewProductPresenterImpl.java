package com.teamadr.ecommerceapp.presenter.product;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.teamadr.ecommerceapp.event_bus.NewProductEvent;
import com.teamadr.ecommerceapp.model.request.product.NewProduct;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;
import com.teamadr.ecommerceapp.view.add_product.NewProductView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class NewProductPresenterImpl implements NewProductPresenter {
    private Context context;
    private NewProductView view;
    private ProductInteractor interactor;

    private int trademarkId;

    public NewProductPresenterImpl(Context context, NewProductView view) {
        this.context = context;
        this.view = view;
        this.interactor = new ProductInteractorImpl(context);
    }

    @Override
    public void setTrademarkId(Integer trademarkId) {
        this.trademarkId = trademarkId;
    }


    @Override
    public void addProduct(NewProduct newProduct) {
        view.showLoadingProgress();
        interactor.addNewProduct(newProduct,
                responseBody -> {
                    view.hideLoadingProgress();
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new NewProductEvent(newProduct));
                },
                Throwable::printStackTrace);
    }

    @Override
    public void refreshTrademark() {
        interactor.refreshTrademarks(null, null, 0, 10,
                pageResponseBody -> {
                    Log.i("trademarks", "accept: " + pageResponseBody.getData());
                    List<TrademarkDto> list = pageResponseBody.getData().getItems();
                    view.refreshTrademark(list);
                },
                throwable -> throwable.printStackTrace());
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

}
