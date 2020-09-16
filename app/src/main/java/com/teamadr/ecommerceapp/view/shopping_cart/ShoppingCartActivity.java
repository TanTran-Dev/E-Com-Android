package com.teamadr.ecommerceapp.view.shopping_cart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.adapter.recycle_view.EndlessLoadingRecyclerViewAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.RecyclerViewAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.ShoppingCartProductAdapter;
import com.teamadr.ecommerceapp.constants.StringConstant;
import com.teamadr.ecommerceapp.event_bus.ShoppingCartEvent;
import com.teamadr.ecommerceapp.model.response.shopping_cart_product.ShoppingCartProductDto;
import com.teamadr.ecommerceapp.presenter.shopping_cart.ShoppingCartPresenter;
import com.teamadr.ecommerceapp.presenter.shopping_cart.ShoppingCartPresenterImpl;
import com.teamadr.ecommerceapp.view.product_detail.ProductDetailActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingCartActivity extends AppCompatActivity implements ShoppingCartView,
        EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        RecyclerViewAdapter.OnItemClickListener,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener,
        SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rcl_shopping_cart_product)
    RecyclerView rclShoppingCartProduct;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tool_bar_title)
    TextView toolbarTitle;
    @BindView(R.id.btnDelete)
    ImageButton btnDelete;
    @BindView(R.id.btnCheckYes)
    ImageButton btnCheckYes;
    @BindView(R.id.btnCheckNo)
    ImageButton btnCheckNo;
    @BindView(R.id.ln_no_data)
    LinearLayout lnNoData;
    @BindView(R.id.lnConfirmDelete)
    LinearLayout lnConfirmDelete;
    @BindView(R.id.chkSelectAll)
    CheckBox chkSelectAll;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerLoading;

    private ShoppingCartPresenter shoppingCartPresenter;
    private ShoppingCartProductAdapter shoppingCartProductAdapter;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            toolbarTitle.setText("Giỏ hàng");
        }

        initPresenter();
        initData();
        intiView();

        shoppingCartPresenter.refreshShoppingCartProducts();
    }

    private void initPresenter() {
        shoppingCartPresenter = new ShoppingCartPresenterImpl(this, this);
    }

    private void initData() {
        userId = getIntent().getStringExtra(StringConstant.KEY_USER_ID);
    }

    private void intiView() {
        shoppingCartProductAdapter = new ShoppingCartProductAdapter(this);
        shoppingCartProductAdapter.setLoadingMoreListener(this);
        shoppingCartProductAdapter.addOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        rclShoppingCartProduct.setLayoutManager(new LinearLayoutManager(this));
        rclShoppingCartProduct.setHasFixedSize(true);
        rclShoppingCartProduct.setAdapter(shoppingCartProductAdapter);

        btnDelete.setOnClickListener(this);
        btnCheckYes.setOnClickListener(this);
        btnCheckNo.setOnClickListener(this);
        chkSelectAll.setOnCheckedChangeListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShoppingCartEvent shoppingCartEvent) {
        shoppingCartPresenter.setShoppingCartId(shoppingCartEvent.getShoppingCartId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void refreshShoppingCartProduct(List<ShoppingCartProductDto> list, int totalItems) {
        shoppingCartProductAdapter.refresh(list);
        if (totalItems == 0) {
            lnNoData.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        } else {
            lnNoData.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showRefreshingProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshingProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addMoreShoppingCartProduct(List<ShoppingCartProductDto> shoppingCartProductDtos) {
        shoppingCartProductAdapter.addModels(shoppingCartProductDtos, false);
    }

    @Override
    public void disableLoadingMore(boolean disable) {
        shoppingCartProductAdapter.enableLoadingMore(!disable);
    }

    @Override
    public void enableLoadingMore(boolean enable) {
        shoppingCartProductAdapter.enableLoadingMore(enable);
    }

    @Override
    public void showLoadingMore(boolean isShow) {
        if (isShow) {
            shoppingCartProductAdapter.showLoadingItem(true);
        } else {
            shoppingCartProductAdapter.hideLoadingItem();
        }
    }

    @Override
    public void showShimmerLoading() {
        shimmerLoading.setVisibility(View.VISIBLE);
        shimmerLoading.startShimmer();
        rclShoppingCartProduct.setVisibility(View.GONE);
    }

    @Override
    public void hideShimmerLoading() {
        shimmerLoading.stopShimmer();
        shimmerLoading.setVisibility(View.GONE);
        rclShoppingCartProduct.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadMore() {
        shoppingCartPresenter.loadMoreShoppingCartProducts();
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder,
                            int viewType, int position) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        ShoppingCartProductDto shoppingCartProductDto = shoppingCartProductAdapter.getItem(position, ShoppingCartProductDto.class);
        intent.putExtra(StringConstant.KEY_PRODUCT_ID, shoppingCartProductDto.getProductDto().getId());
        intent.putExtra(StringConstant.KEY_HIDE_BUTTON, true);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        shoppingCartPresenter.refreshShoppingCartProducts();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete: {
                lnConfirmDelete.setVisibility(View.VISIBLE);
                chkSelectAll.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.GONE);

                shoppingCartProductAdapter.setSelectedMode(true);
            }
            break;

            case R.id.btnCheckYes: {
                showConfirmDialog();
            }
            break;

            case R.id.btnCheckNo: {
                hideOption();
            }
            break;
        }
    }

    private void hideOption() {
        btnDelete.setVisibility(View.VISIBLE);
        lnConfirmDelete.setVisibility(View.GONE);
        chkSelectAll.setVisibility(View.GONE);
        shoppingCartProductAdapter.setSelectedMode(false);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        for (int i = 0; i < shoppingCartProductAdapter.getListWrapperModels().size(); i++) {
            if (isChecked) {
                shoppingCartProductAdapter.setSelectedItem(i, true);
            } else {
                shoppingCartProductAdapter.setSelectedItem(i, false);
            }
            shoppingCartProductAdapter.notifyDataSetChanged();
        }

    }

    private void showConfirmDialog() {
        new iOSDialogBuilder(this)
                .setTitle("Xác nhận xoá!")
                .setSubtitle("Bạn chắc chắn muốn xoá sản phẩm này?")
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener("Xác nhận", dialog -> {
                    List<ShoppingCartProductDto> list =
                            shoppingCartProductAdapter.getSelectedItemModel(ShoppingCartProductDto.class);
                    List<String> listShoppingCartProductId = new ArrayList<>();

                    for (ShoppingCartProductDto shoppingCartProductDto : list) {
                        listShoppingCartProductId.add(shoppingCartProductDto.getId());
                    }

                    shoppingCartPresenter.deleteShoppingCartProducts(listShoppingCartProductId);
                    hideOption();
                    dialog.dismiss();
                })
                .setNegativeListener("Huỷ", iOSDialog::dismiss)
                .build().show();
    }
}
