package com.teamadr.ecommerceapp.presenter.auth;

import com.teamadr.ecommerceapp.presenter.BasePresenter;

public interface AuthUsernamePasswordPresenter extends BasePresenter {
    void handleLogin(String username, String password);
}
