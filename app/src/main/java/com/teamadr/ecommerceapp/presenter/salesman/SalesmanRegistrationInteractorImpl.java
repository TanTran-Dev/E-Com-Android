package com.teamadr.ecommerceapp.presenter.salesman;

import android.content.Context;

import com.teamadr.ecommerceapp.model.request.salesman.NewSalesmanDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.service.api.salesman.SalesmanRegService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SalesmanRegistrationInteractorImpl implements SalesmanRegistrationInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public SalesmanRegistrationInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void registerNewAdminByEmail(NewSalesmanDto newSalesmanDto,
                                        Consumer<ResponseBody> onSuccess,
                                        Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(SalesmanRegService.class)
                .registrationNewSalesman(newSalesmanDto)
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
