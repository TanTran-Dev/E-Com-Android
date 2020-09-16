package com.teamadr.ecommerceapp.presenter.auth;

import com.teamadr.ecommerceapp.model.request.auth.UsernamePasswordDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.auth.AuthenticationResult;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import io.reactivex.functions.Consumer;

public interface AuthUsernamePasswordInteractor extends BaseInteractor {
    void getAuth(String client_id, String secret,
                 UsernamePasswordDto usernamePasswordDto,
                 Consumer<ResponseBody<AuthenticationResult>> onSuccess,
                 Consumer<Throwable> onError);
}
