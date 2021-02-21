package com.teamadr.ecommerceapp.presenter.shopping_cart;

import android.content.Context;

import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.shopping_cart_product.ShoppingCartProductDto;
import com.teamadr.ecommerceapp.service.api.sale_product.ShoppingCartService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;
import com.teamadr.ecommerceapp.utils.UserAuth;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShoppingCartInteractorImpl implements ShoppingCartInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ShoppingCartInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void refreshShoppingCartProduct(List<String> sortBy, List<String> sortType,
                                           int pageIndex, int pageSize,
                                           Consumer<ResponseBody<Page<ShoppingCartProductDto>>> onSuccess, Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(ShoppingCartService.class)
                .refreshListShoppingCartProduct(UserAuth.getUserId(context), sortBy, sortType, pageIndex, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void deleteListShoppingCartProduct(List<String> shoppingCartProductIDs, Consumer<ResponseBody> onSuccess, Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(ShoppingCartService.class)
                .deleteListShoppingCartProduct(shoppingCartProductIDs)
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
