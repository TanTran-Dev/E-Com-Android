package com.teamadr.ecommerceapp.presenter.auth;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.request.auth.UsernamePasswordDto;
import com.teamadr.ecommerceapp.model.response.auth.AuthenticationResult;
import com.teamadr.ecommerceapp.utils.UserAuth;
import com.teamadr.ecommerceapp.view.login.AuthenticationView;


public class AuthUsernamePasswordPresenterImpl implements AuthUsernamePasswordPresenter {
    private Context context;
    private AuthenticationView view;
    private AuthUsernamePasswordInteractor authUsernamePasswordInteractor;

    public AuthUsernamePasswordPresenterImpl(Context context, AuthenticationView view) {
        this.context = context;
        this.view = view;
        this.authUsernamePasswordInteractor = new AuthUsernamePasswordInteractorImpl(context);
    }

    @Override
    public void handleLogin(String username, String password) {
        if (username == null || username.isEmpty()) {
            return;
        }

        if (password == null || password.isEmpty()) {
            return;
        }

        view.showLoadingProgress();

        authUsernamePasswordInteractor.getAuth(RequestContansts.CLIENT_ID, RequestContansts.SECRET,
                new UsernamePasswordDto(username, password),
                authenticationResultResponseBody -> {
                    Log.i("GGG", "accept: " + authenticationResultResponseBody.getData().toString());
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    AuthenticationResult authenticationResult = authenticationResultResponseBody.getData();
                    //Save accessToken vs RefreshToken
                    UserAuth.saveAccessToken(context, authenticationResult);
                    UserAuth.saveUser(context, authenticationResult.getUserID(), authenticationResult.getUserType().getLabel());

                    view.hideLoadingProgress();
                    view.navigateHome();
                },
                throwable -> {
                    view.hideLoadingProgress();
                    throwable.printStackTrace();
                    Toast.makeText(context, "Sai tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onViewDestroy() {
        authUsernamePasswordInteractor.onViewDestroy();
    }
}
