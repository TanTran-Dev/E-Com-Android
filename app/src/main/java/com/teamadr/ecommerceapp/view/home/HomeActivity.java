package com.teamadr.ecommerceapp.view.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.adapter.recycle_view.TrademarkAdapter;
import com.teamadr.ecommerceapp.adapter.view_pager.HomeFragmentPagerAdapter;
import com.teamadr.ecommerceapp.constants.SortType;
import com.teamadr.ecommerceapp.constants.StringConstant;
import com.teamadr.ecommerceapp.constants.UserType;
import com.teamadr.ecommerceapp.event_bus.SortEvent;
import com.teamadr.ecommerceapp.event_bus.UserProfileEvent;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;
import com.teamadr.ecommerceapp.presenter.home.HomePresenter;
import com.teamadr.ecommerceapp.presenter.home.HomePresenterImpl;
import com.teamadr.ecommerceapp.utils.UserAuth;
import com.teamadr.ecommerceapp.view.add_product.NewProductActivity;
import com.teamadr.ecommerceapp.view.login.LoginActivity;
import com.teamadr.ecommerceapp.view.order_product.OrderProductActivity;
import com.teamadr.ecommerceapp.view.product.ProductByAdminActivity;
import com.teamadr.ecommerceapp.view.shopping_cart.ShoppingCartActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;

public class HomeActivity extends AppCompatActivity implements HomeView,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.fab_menu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.float_button_filter)
    FloatingActionButton fabFilter;
    @BindView(R.id.float_button_add)
    FloatingActionButton fabAdd;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tool_bar_title)
    TextView toolbarTitle;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private Spinner spnTrademark;
    private Spinner spnFilterPrice;
    private Button btnConfirm;
    private Button btnCancel;
    private SearchView searchView;

    private ImageView imgCover;
    private ImageView imgAvatar;
    private TextView txtFullName;
    private TextView txtAddress;

    private HomePresenter homePresenter;
    private TrademarkAdapter trademarkAdapter;
    private List<TrademarkDto> listTrademark;
    private List<String> filterPrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            toolbarTitle.setText(R.string.app_name);
        }
        initPresenter();
        initFragment();
        initData();
        initNavigationDrawer();
        initView();

        homePresenter.refreshTrademark();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initPresenter() {
        homePresenter = new HomePresenterImpl(this, this);
    }

    private void initData() {
        listTrademark = new ArrayList<>();
    }

    private void initView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black_1000));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        navigationView.setNavigationItemSelectedListener(this);
        imgAvatar.setOnClickListener(this);
        imgCover.setOnClickListener(this);

        if (UserType.CUSTOMER.getLabel().equals(UserAuth.getUserType(this))) {
            fabAdd.setVisibility(View.GONE);
            fabFilter.setVisibility(View.VISIBLE);
        }

        if (UserType.ADMIN.getLabel().equals(UserAuth.getUserType(this))) {
            fabAdd.setVisibility(View.VISIBLE);
            fabFilter.setVisibility(View.VISIBLE);
        }


        fabFilter.setOnClickListener(this);
        fabAdd.setOnClickListener(this);
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragmentPagerAdapter homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(fragmentManager, 2);
        viewPager.setAdapter(homeFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.setSelectedItemId(HomeFragmentPagerAdapter.getItemID(position));
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initNavigationDrawer() {
        View userHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_auth);

        if (UserType.CUSTOMER.getLabel().equals(UserAuth.getUserType(this))) {
            navigationView.inflateMenu(R.menu.menu_navigation);
        }

        if (UserType.ADMIN.getLabel().equals(UserAuth.getUserType(this))) {
            navigationView.inflateMenu(R.menu.menu_navigation_for_admin);
        }

        imgAvatar = userHeaderView.findViewById(R.id.imgAvatar);
        imgCover = userHeaderView.findViewById(R.id.imgCover);
        txtFullName = userHeaderView.findViewById(R.id.txtFullName);
        txtAddress = userHeaderView.findViewById(R.id.txtDeliveryAddress);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserProfileEvent profileEvent) {
        String avatarUrl = profileEvent.getAvatarUrl();
        String coverUrl = profileEvent.getCoverUrl();
        String fullName = profileEvent.getName();
        String address = profileEvent.getAddress();

        if (profileEvent.getAvatarUrl() != null){
            Glide.with(this).load(avatarUrl)
                    .into(imgAvatar);
        }else {
            imgAvatar.setImageResource(R.drawable.avatar_placeholder);
        }

        if (profileEvent.getCoverUrl() != null){
            Glide.with(this).load(coverUrl)
                    .into(imgCover);
        }else {
            imgCover.setImageResource(R.drawable.wall_placeholder);
        }

        txtFullName.setText(fullName);
        txtAddress.setText(address);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (UserType.CUSTOMER.getLabel().equals(UserAuth.getUserType(this))) {
            getMenuInflater().inflate(R.menu.menu_toolbar, menu);
//            searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
//            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            break;

            case R.id.menu_cart: {
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                intent.putExtra(StringConstant.KEY_USER_ID, UserAuth.getUserId(this));
                startActivity(intent);
            }
            break;

            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.bottom_nav_home: {
                            viewPager.setCurrentItem(HomeFragmentPagerAdapter.PRODUCT_FRAGMENT_POSITION);
                            fabMenu.setVisibility(View.VISIBLE);
                        }
                        break;
                        case R.id.bottom_nav_profile: {
                            viewPager.setCurrentItem(HomeFragmentPagerAdapter.PROFILE_FRAGMENT_POSITION);
                            fabMenu.setVisibility(View.GONE);
                        }
                        break;
                        default:
                            return false;
                    }
                    return true;
                }
            };

    public void dialogFilter() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_filter);
        dialog.show();
        //mapping
        spnTrademark = dialog.findViewById(R.id.spnTrademark);
        spnFilterPrice = dialog.findViewById(R.id.spnFilterPrice);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        spnFilterPrice = dialog.findViewById(R.id.spnFilterPrice);

        trademarkAdapter = new TrademarkAdapter(this, listTrademark);
        spnTrademark.setAdapter(trademarkAdapter);

        //add list spinner filter by price
        filterPrices = new ArrayList<>();
        filterPrices.add(SortType.ASCENDING.getLabel());
        filterPrices.add(SortType.DESCENDING.getLabel());
        filterPrices.add(SortType.NULL.getLabel());

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this,
                R.layout.item_filter_price, filterPrices);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFilterPrice.setAdapter(spinnerAdapter);

        btnConfirm.setOnClickListener(v -> {
            TrademarkDto trademarkDto = listTrademark.get(spnTrademark.getSelectedItemPosition());

            String filterPrice = filterPrices.get(spnFilterPrice.getSelectedItemPosition());

            //push event to product fragment
            if (SortType.ASCENDING.getLabel().equals(filterPrice)) {
                EventBus.getDefault().post(new SortEvent(Arrays.asList("price"),
                        Arrays.asList(SortType.ASCENDING.getValue()), trademarkDto.getId()));
            }

            if (SortType.DESCENDING.getLabel().equals(filterPrice)) {
                EventBus.getDefault().post(new SortEvent(Arrays.asList("price"),
                        Arrays.asList(SortType.DESCENDING.getValue()), trademarkDto.getId()));
            }

            if (SortType.NULL.getLabel().equals(filterPrice)) {
                EventBus.getDefault().post(new SortEvent(Arrays.asList("price"),
                        Arrays.asList(SortType.NULL.getValue()), trademarkDto.getId()));
            }

            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    public void refreshTrademark(List<TrademarkDto> trademarkDtos) {
        listTrademark.clear();
        listTrademark.addAll(trademarkDtos);
        listTrademark.add(new TrademarkDto(0, "", "Không"));

        trademarkAdapter.notifyDataSetChanged();
    }

    @Override
    public void logout() {
        UserAuth.clearAllSaveData(this);
    }

    @Override
    public void navigateLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout: {
                homePresenter.logout();
            }
            break;

            case R.id.nav_view_home: {
                navigateToHome();
            }
            break;

            case R.id.nav_list_orders: {
                Intent intent = new Intent(this, OrderProductActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.nav_product: {
                Intent intent = new Intent(this, ProductByAdminActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.nav_view_rate:
            case R.id.nav_change_password:
            case R.id.nav_share:
            case R.id.nav_rate_app: {
                Toast.makeText(this, "Tính năng này vẫn chưa được hoàn thiện", Toast.LENGTH_SHORT).show();
            }
            break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgCover:
            case R.id.imgAvatar: {
                navigateToProfile();
            }
            break;

            case R.id.float_button_filter: {
                dialogFilter();
            }
            break;

            case R.id.float_button_add: {
                Intent intent = new Intent(this, NewProductActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    private void navigateToProfile() {
        int profilePos = viewPager.getChildCount() - 1;
        int currentPos = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentPos + profilePos);
        drawerLayout.closeDrawers();
    }

    private void navigateToHome() {
        viewPager.setCurrentItem(0);
        drawerLayout.closeDrawers();
    }
}
