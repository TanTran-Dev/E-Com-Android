package com.teamadr.ecommerceapp.view.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.teamadr.ecommerceapp.presenter.BasePresenter;

public abstract class BaseActivity <T extends BasePresenter> extends AppCompatActivity {
    private T presenter;

    protected abstract int getLayoutIntResource();

    protected abstract void initVariables(Bundle saveInstanceState);

    protected abstract void initData(Bundle saveInstanceState);

    protected abstract T initPresenter();

    protected T getPresenter(){
        return presenter;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutIntResource());
        this.presenter = initPresenter();
        initVariables(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null){
            presenter.onViewDestroy();
        }
    }
}
