package com.teamadr.ecommerceapp.view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.teamadr.ecommerceapp.presenter.BasePresenter;

public abstract class BaseFragment <T extends BasePresenter> extends Fragment {
    private T presenter;

    protected abstract int getLayoutIntResource();

    protected abstract void initVariables(Bundle saveInstanceState, View rootView);

    protected abstract void initData(Bundle saveInstanceState);

    protected abstract T initPresenter();

    public String getName(){
        return getClass().getName();
    }

    public T getPresenter(){
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutIntResource(), container, false);
        presenter = initPresenter();
        initVariables(savedInstanceState, view);
        initData(savedInstanceState);
        return view;
    }
}
