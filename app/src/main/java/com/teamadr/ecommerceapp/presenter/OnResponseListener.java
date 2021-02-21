package com.teamadr.ecommerceapp.presenter;

import com.teamadr.ecommerceapp.model.response.ResponseBody;

public interface OnResponseListener<T extends ResponseBody, U extends ResponseBody> {
    void onSuccess(T body);
    void onError(U body);
    void onError();
    void onNetworkConnectionError();
    void onTimeOut();
}
