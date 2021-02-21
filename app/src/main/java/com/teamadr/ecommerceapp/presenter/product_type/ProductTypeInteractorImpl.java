package com.teamadr.ecommerceapp.presenter.product_type;

import android.content.Context;

import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.service.api.sale_product.ProductTypeService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProductTypeInteractorImpl implements ProductTypeInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ProductTypeInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void refreshProductTypes(String sortBy, String sortType, int pageIndex, int pageSize,
                                    Consumer<ResponseBody<Page<ProductTypeDto>>> onSuccess,
                                    Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(ProductTypeService.class)
                .getListProductType(sortBy, sortType, pageIndex, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        compositeDisposable.clear();
    }
}
