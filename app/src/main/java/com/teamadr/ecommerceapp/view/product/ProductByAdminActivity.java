package com.teamadr.ecommerceapp.view.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.adapter.recycle_view.EndlessLoadingRecyclerViewAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.ProductAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.RecyclerViewAdapter;
import com.teamadr.ecommerceapp.constants.StringConstant;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.presenter.product.ProductByAdminPresenter;
import com.teamadr.ecommerceapp.presenter.product.ProductByAdminPresenterImpl;
import com.teamadr.ecommerceapp.view.product_detail.ProductDetailActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductByAdminActivity extends AppCompatActivity implements ProductByAdminView,
        View.OnClickListener, EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        SwipeRefreshLayout.OnRefreshListener, RecyclerViewAdapter.OnItemClickListener,
        CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rcl_product)
    RecyclerView rclProduct;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tool_bar_title)
    TextView toolbarTitle;
    @BindView(R.id.ln_no_data)
    LinearLayout lnNoData;

    @BindView(R.id.btnDelete)
    ImageButton btnDelete;
    @BindView(R.id.btnCheckYes)
    ImageButton btnCheckYes;
    @BindView(R.id.btnCheckNo)
    ImageButton btnCheckNo;
    @BindView(R.id.lnConfirmDelete)
    LinearLayout lnConfirmDelete;
    @BindView(R.id.chkSelectAll)
    CheckBox chkSelectAll;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerLoading;

    private ProductByAdminPresenter productByAdminPresenter;
    private ProductAdapter productAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_admin);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            toolbarTitle.setText("Danh sách sản phẩm");
        }

        initPresenter();
        initView();

        productByAdminPresenter.refreshPageProductByAdmin();
    }

    private void initPresenter() {
        productByAdminPresenter = new ProductByAdminPresenterImpl(this, this);
    }

    private void initView() {
        productAdapter = new ProductAdapter(this);
        productAdapter.addOnItemClickListener(this);
        productAdapter.setLoadingMoreListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        rclProduct.setLayoutManager(new LinearLayoutManager(this));
        rclProduct.setHasFixedSize(true);
        rclProduct.setAdapter(productAdapter);

        btnDelete.setOnClickListener(this);
        btnCheckYes.setOnClickListener(this);
        btnCheckNo.setOnClickListener(this);
        chkSelectAll.setOnCheckedChangeListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete: {
                lnConfirmDelete.setVisibility(View.VISIBLE);
                chkSelectAll.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.GONE);

                productAdapter.setSelectedMode(true);
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

    private void showConfirmDialog() {
        new iOSDialogBuilder(this)
                .setTitle("Xác nhận xoá!")
                .setSubtitle("Bạn chắc chắn muốn xoá sản phẩm này?")
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener("Xác nhận", dialog -> {
                    List<ProductDto> list =
                            productAdapter.getSelectedItemModel(ProductDto.class);
                    List<Integer> productIds = new ArrayList<>();

                    for (ProductDto productDto : list) {
                        productIds.add(productDto.getId());
                    }

                    productByAdminPresenter.deleteProducts(productIds);
                    hideOption();
                    dialog.dismiss();
                })
                .setNegativeListener("Huỷ", iOSDialog::dismiss)
                .build().show();
    }

    private void hideOption() {
        btnDelete.setVisibility(View.VISIBLE);
        lnConfirmDelete.setVisibility(View.GONE);
        chkSelectAll.setVisibility(View.GONE);
        productAdapter.setSelectedMode(false);
    }

    @Override
    public void onLoadMore() {
        productByAdminPresenter.loadMoreProduct();
    }

    @Override
    public void onRefresh() {
        productByAdminPresenter.refreshPageProductByAdmin();
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter,
                            RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        ProductDto productDto = productAdapter.getItem(position, ProductDto.class);
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra(StringConstant.KEY_PRODUCT_ID, productDto.getId());
        startActivity(intent);
    }

    @Override
    public void refreshProductByAdmin(List<ProductDto> list) {
        productAdapter.refresh(list);
    }

    @Override
    public void addMoreProducts(List<ProductDto> productDtos) {
        productAdapter.addModels(productDtos, false);
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
    public void showLoadingProgress() {

    }

    @Override
    public void hideLoadingProgress() {

    }

    @Override
    public void showLoadingMore(boolean isShow) {
        if (isShow) {
            productAdapter.showLoadingItem(true);
        } else {
            productAdapter.hideLoadingItem();
        }
    }

    @Override
    public void disableLoadingMore(boolean disable) {
        productAdapter.enableLoadingMore(!disable);
    }

    @Override
    public void enableLoadingMore(boolean enable) {
        productAdapter.enableLoadingMore(enable);
    }

    @Override
    public void showShimmerLoading() {
        shimmerLoading.setVisibility(View.VISIBLE);
        shimmerLoading.startShimmer();
        rclProduct.setVisibility(View.GONE);
    }

    @Override
    public void hideShimmerLoading() {
        shimmerLoading.stopShimmer();
        shimmerLoading.setVisibility(View.GONE);
        rclProduct.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        for (int i = 0; i < productAdapter.getListWrapperModels().size(); i++) {
            if (isChecked) {
                productAdapter.setSelectedItem(i, true);
            } else {
                productAdapter.setSelectedItem(i, false);
            }
            productAdapter.notifyDataSetChanged();
        }
    }
}
