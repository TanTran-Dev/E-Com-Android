package com.teamadr.ecommerceapp.presenter.customer;

import android.content.Context;

import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.service.api.customer.CustomerRegService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CustomerRegistrationInteractorImpl implements CustomerRegistrationInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public CustomerRegistrationInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void registrationNewCustomer(NewCustomerDto newCustomerDto,
                                        Consumer<ResponseBody> onSuccess,
                                        Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(CustomerRegService.class)
                .registrationNewCustomer(newCustomerDto)
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
