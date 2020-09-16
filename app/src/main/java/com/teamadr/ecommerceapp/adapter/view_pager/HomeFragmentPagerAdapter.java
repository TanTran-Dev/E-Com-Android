package com.teamadr.ecommerceapp.adapter.view_pager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.view.home.HomeFragment;
import com.teamadr.ecommerceapp.view.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
    public static final int PRODUCT_FRAGMENT_POSITION = 0;
    public static final int PROFILE_FRAGMENT_POSITION = 1;

    private List<Fragment> fragments;

    public HomeFragmentPagerAdapter(@NonNull FragmentManager fm, int size) {
        super(fm);
        fragments = new ArrayList<>(size);
        fragments.add(new HomeFragment());
        fragments.add(new ProfileFragment());
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

    public static int getItemID(int id) {
        switch (id) {
            case PRODUCT_FRAGMENT_POSITION: {
                return R.id.bottom_nav_home;
            }
            case PROFILE_FRAGMENT_POSITION: {
                return R.id.bottom_nav_profile;
            }
            default: {
                return -1;
            }
        }
    }
}
