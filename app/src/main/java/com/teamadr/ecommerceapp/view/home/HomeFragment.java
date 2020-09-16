package com.teamadr.ecommerceapp.view.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.adapter.view_pager.TabProductPagerAdapter;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.presenter.product_type.ProductTypePresenter;
import com.teamadr.ecommerceapp.presenter.product_type.ProductTypePresenterImpl;
import com.teamadr.ecommerceapp.view.product_type.ProductTypeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements ProductTypeView {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private ProductTypePresenter productTypePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        productTypePresenter = new ProductTypePresenterImpl(getContext(), this);
        productTypePresenter.refreshProductType();
        return view;
    }

    @Override
    public void refreshListProductType(List<ProductTypeDto> productTypeDtos) {
        assert getFragmentManager() != null;
        TabProductPagerAdapter pagerAdapter = new TabProductPagerAdapter(getChildFragmentManager(), productTypeDtos);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
