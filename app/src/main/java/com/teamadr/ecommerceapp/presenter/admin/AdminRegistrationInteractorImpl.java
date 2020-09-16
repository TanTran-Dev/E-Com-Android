package com.teamadr.ecommerceapp.presenter.admin;

import android.content.Context;

import com.teamadr.ecommerceapp.model.request.admin.NewAdminDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.service.api.AdminRegService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AdminRegistrationInteractorImpl implements AdminRegistrationInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public AdminRegistrationInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void registerNewAdminByEmail(NewAdminDto newAdminDto,
                                        Consumer<ResponseBody> onSuccess,
                                        Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(AdminRegService.class)
                .registrationNewAdmin(newAdminDto)
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
