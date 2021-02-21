package com.teamadr.ecommerceapp.presenter.admin_profile;

import android.content.Context;

import com.teamadr.ecommerceapp.model.request.salesman.NewSalesmanDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.salesman.SalesmanDto;
import com.teamadr.ecommerceapp.service.api.salesman.SalesmanProfileService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;
import com.teamadr.ecommerceapp.utils.UserAuth;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AdminProfileInteractorImpl implements AdminProfileInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public AdminProfileInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getProfileSalesman(String token,
                                   Consumer<ResponseBody<SalesmanDto>> onSuccess,
                                   Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(SalesmanProfileService.class)
                .getProfileSalesman(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void updateProfileSalesman(String adminId, NewSalesmanDto newSalesmanDto,
                                      Consumer<ResponseBody> onSuccess,
                                      Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(SalesmanProfileService.class)
                .updateProfileSalesman(UserAuth.getBearerToken(context), adminId, newSalesmanDto)
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
