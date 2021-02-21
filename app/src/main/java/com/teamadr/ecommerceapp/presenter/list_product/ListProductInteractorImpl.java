package com.teamadr.ecommerceapp.presenter.list_product;

import android.content.Context;

import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;
import com.teamadr.ecommerceapp.presenter.OnResponseListener;
import com.teamadr.ecommerceapp.service.api.ResponseObserver;
import com.teamadr.ecommerceapp.service.api.sale_product.ProductService;
import com.teamadr.ecommerceapp.service.api.sale_product.ProductTypeService;
import com.teamadr.ecommerceapp.service.api.sale_product.TrademarkService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ListProductInteractorImpl implements ListProductInteractor{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ListProductInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void refreshProductTypes(int pageIndex, int pageSize,
                                    OnResponseListener<ResponseBody<Page<ProductTypeDto>>, ResponseBody> listener) {
        Disposable disposable = APIClient.getInstance()
                .create(ProductTypeService.class)
                .getListProductType(null, null, pageIndex, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<>(listener));
        compositeDisposable.add(disposable);
    }

    @Override
    public void refreshTrademarks(int pageIndex, int pageSize,
                                  OnResponseListener<ResponseBody<Page<TrademarkDto>>, ResponseBody> listener) {
        Disposable disposable = APIClient.getInstance()
                .create(TrademarkService.class)
                .getTrademarks(null, null, pageIndex, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<>(listener));
        compositeDisposable.add(disposable);
    }

    @Override
    public void refreshListProduct(Integer productTypeId, Integer trademarkId,
                                   int pageIndex, int pageSize,
                                   OnResponseListener<ResponseBody<Page<ProductDto>>, ResponseBody> listener) {
        Disposable disposable = APIClient.getInstance()
                .create(ProductService.class)
                .getListProducts(productTypeId, trademarkId, null, null, pageIndex, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(new ResponseObserver<>(listener));
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        compositeDisposable.clear();
    }
}
