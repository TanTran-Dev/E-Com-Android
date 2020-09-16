package com.teamadr.ecommerceapp.adapter.view_pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.teamadr.ecommerceapp.constants.StringConstant;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.view.product.ProductFragment;

import java.util.ArrayList;
import java.util.List;

public class TabProductPagerAdapter extends FragmentPagerAdapter {
    private List<ProductFragment> fragments;
    private List<ProductTypeDto> productTypeDtos;

    public TabProductPagerAdapter(@NonNull FragmentManager fm, List<ProductTypeDto> productTypeDtos) {
        super(fm);
        this.fragments = new ArrayList<>();
        this.productTypeDtos = productTypeDtos;

        for (ProductTypeDto productTypeDto : productTypeDtos){
            this.fragments.add(createNewFragment(productTypeDto));
        }
    }

    private ProductFragment createNewFragment(ProductTypeDto productTypeDto){
        ProductFragment result = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt(StringConstant.KEY_PRODUCT_TYPE_ID, productTypeDto.getId());
        result.setArguments(args);
        return result;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return productTypeDtos.get(position).getName();
    }
}
