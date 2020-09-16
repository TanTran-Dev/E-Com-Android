package com.teamadr.ecommerceapp.presenter.auth;

import android.content.Context;

import com.teamadr.ecommerceapp.model.request.auth.UsernamePasswordDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.auth.AuthenticationResult;
import com.teamadr.ecommerceapp.service.api.AuthenticationService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AuthUsernamePasswordInteractorImpl implements AuthUsernamePasswordInteractor{
    private Context context;
    private CompositeDisposable compositeDisposable;

    public AuthUsernamePasswordInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getAuth(String client_id, String secret,
                        UsernamePasswordDto usernamePasswordDto,
                        Consumer<ResponseBody<AuthenticationResult>> onSuccess,
                        Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(AuthenticationService.class)
                .getAuth(client_id, secret, usernamePasswordDto)
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
