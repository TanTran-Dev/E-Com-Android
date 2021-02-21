package com.teamadr.ecommerceapp.presenter.order_product;

import android.content.Context;

import com.teamadr.ecommerceapp.model.request.order_product.OrderProductDto;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.service.api.sale_product.OrderProductService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;
import com.teamadr.ecommerceapp.utils.UserAuth;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderProductInteractorImpl implements OrderProductInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public OrderProductInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void getListOrderProduct(List<String> sortBy, List<String> sortType,
                                    int pageIndex, int pageSize,
                                    Consumer<ResponseBody<Page<OrderProductDto>>> onSuccess,
                                    Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(OrderProductService.class)
                .getListOrderProduct(UserAuth.getUserId(context), sortBy, sortType, pageIndex, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void deleteListOrderProducts(List<String> orderProductIds,
                                        Consumer<ResponseBody> onSuccess,
                                        Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(OrderProductService.class)
                .deleteListOrderProducts(orderProductIds)
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
