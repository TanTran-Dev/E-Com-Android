package com.teamadr.ecommerceapp.service.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.teamadr.ecommerceapp.exception.NoInternetConnectionException;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.presenter.OnResponseListener;

import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;

public class ResponseObserver<T extends ResponseBody, U extends ResponseBody> extends DisposableObserver<T> {
    public static final String TAG = "REQUEST_RESPONSE";
    private OnResponseListener<T, U> listener;

    public ResponseObserver(OnResponseListener<T, U> listener) {
        this.listener = listener;
    }

    @Override
    public void onNext(T body) {
        listener.onSuccess(body);
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, e.getMessage(), e);
        if (e instanceof NoInternetConnectionException) {
            listener.onNetworkConnectionError();
        } else if (e instanceof SocketTimeoutException) {
            listener.onTimeOut();
        } else if (e instanceof HttpException) {
            Gson gson = new GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .create();
            try {
                String responseBodyString = ((HttpException) e).response().errorBody().string();
                listener.onError((U) gson.fromJson(responseBodyString, ResponseBody.class));
            } catch (Exception e1) {
                e1.printStackTrace();
                listener.onError();
            }
        }
    }

    @Override
    public void onComplete() {

    }
}
