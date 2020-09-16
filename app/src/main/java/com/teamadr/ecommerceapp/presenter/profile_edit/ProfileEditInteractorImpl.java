package com.teamadr.ecommerceapp.presenter.profile_edit;

import android.content.Context;

import com.teamadr.ecommerceapp.model.request.admin.NewAdminDto;
import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.service.api.AdminProfileService;
import com.teamadr.ecommerceapp.service.api.CustomerProfileService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;
import com.teamadr.ecommerceapp.utils.UserAuth;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProfileEditInteractorImpl implements ProfileEditInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ProfileEditInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void updateProfileAdmin(String adminId, NewAdminDto newAdminDto,
                                   Consumer<ResponseBody> onSuccess,
                                   Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(AdminProfileService.class)
                .updateProfileAdmin(UserAuth.getBearerToken(context), adminId, newAdminDto)
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
