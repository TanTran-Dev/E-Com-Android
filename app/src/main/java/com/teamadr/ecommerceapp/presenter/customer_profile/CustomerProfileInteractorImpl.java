package com.teamadr.ecommerceapp.presenter.customer_profile;

import android.content.Context;

import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.customer.CustomerDto;
import com.teamadr.ecommerceapp.service.api.customer.CustomerProfileService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;
import com.teamadr.ecommerceapp.utils.UserAuth;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CustomerProfileInteractorImpl implements CustomerProfileInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public CustomerProfileInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getProfileCustomer(String token,
                                   Consumer<ResponseBody<CustomerDto>> onSuccess,
                                   Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(CustomerProfileService.class)
                .getProfileCustomer(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void updateProfileCustomer(String customerId, NewCustomerDto newCustomerDto,
                                      Consumer<ResponseBody> onSuccess,
                                      Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(CustomerProfileService.class)
                .updateProfileCustomer(UserAuth.getBearerToken(context), customerId, newCustomerDto)
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
