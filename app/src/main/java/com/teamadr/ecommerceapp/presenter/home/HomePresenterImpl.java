package com.teamadr.ecommerceapp.presenter.home;

import android.content.Context;
import android.util.Log;

import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;
import com.teamadr.ecommerceapp.view.home.HomeView;

import java.util.List;


public class HomePresenterImpl implements HomePresenter {
    private Context context;
    private HomeView view;
    private HomeInteractor homeInteractor;

    public HomePresenterImpl(Context context, HomeView view) {
        this.context = context;
        this.view = view;
        this.homeInteractor = new HomeInteractorImpl(context);
    }

    @Override
    public void refreshTrademark() {
        homeInteractor.refreshTrademarks(null, null, 0, 10,
                pageResponseBody -> {
                    Log.i("trademarks", "accept: " + pageResponseBody.getData());
                    List<TrademarkDto> list = pageResponseBody.getData().getItems();
                    view.refreshTrademark(list);
                },

                throwable -> throwable.printStackTrace());
    }


    @Override
    public void logout() {
        view.logout();
        view.navigateLogin();
    }

    @Override
    public void onViewDestroy() {
        homeInteractor.onViewDestroy();
    }
}
