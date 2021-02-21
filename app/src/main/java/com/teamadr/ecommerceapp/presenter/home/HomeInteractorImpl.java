package com.teamadr.ecommerceapp.presenter.home;

import android.content.Context;

import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;
import com.teamadr.ecommerceapp.service.api.sale_product.TrademarkService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeInteractorImpl implements HomeInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public HomeInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void refreshTrademarks(List<String> sortBy, List<String> sortType, int pageIndex, int pageSize,
                                  Consumer<ResponseBody<Page<TrademarkDto>>> onSuccess,
                                  Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(TrademarkService.class)
                .getTrademarks(sortBy, sortType, pageIndex, pageSize)
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
