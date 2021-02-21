package com.teamadr.ecommerceapp.view.list_product;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.adapter.recycle_view.ListProductsAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.base.EndlessLoadingRecyclerViewAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.base.RecyclerViewAdapter;
import com.teamadr.ecommerceapp.custom_view.LoadingDialog;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;
import com.teamadr.ecommerceapp.presenter.list_product.ListProductPresenterImpl;
import com.teamadr.ecommerceapp.view.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListProductsFragment extends BaseFragment<ListProductPresenterImpl>
        implements ListProductView, RecyclerViewAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rcl_products)
    RecyclerView rclProducts;
    @BindView(R.id.ln_no_data)
    LinearLayout lnNoData;

    private LoadingDialog loadingDialog;
    private ListProductsAdapter listProductsAdapter;

    @Override
    protected int getLayoutIntResource() {
        return R.layout.list_product_fragment;
    }

    @Override
    protected void initVariables(Bundle saveInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
        loadingDialog = new LoadingDialog(getContext());
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        Context context = getActivity();
        listProductsAdapter = new ListProductsAdapter(context);
        listProductsAdapter.addOnItemClickListener(this);
        listProductsAdapter.setLoadingMoreListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rclProducts.setLayoutManager(layoutManager);
        rclProducts.setAdapter(listProductsAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        getPresenter().refreshPage();
        lnNoData.setVisibility(View.GONE);
        rclProducts.scrollToPosition(0);
    }

    @Override
    protected ListProductPresenterImpl initPresenter() {
        return new ListProductPresenterImpl(getContext(), this);
    }


    @Override
    public void refreshListProducts(List<ProductDto> products) {
        listProductsAdapter.refreshDataHolder(ListProductsAdapter.VIEW_TYPE_PRODUCTS_LABEL, products);
        checkNoData();
    }

    @Override
    public void refreshListProductType(List<ProductTypeDto> productTypes) {
        listProductsAdapter.refreshDataHolder(ListProductsAdapter.VIEW_TYPE_PRODUCT_TYPE, productTypes);
        checkNoData();
    }

    @Override
    public void refreshListTrademark(List<TrademarkDto> trademarks) {
        listProductsAdapter.refreshDataHolder(ListProductsAdapter.VIEW_TYPE_TRADEMARK, trademarks);
        checkNoData();
    }

    @Override
    public void addProducts(List<ProductDto> products) {
        listProductsAdapter.addModels(products, false);
    }

    public void checkNoData() {
        if (listProductsAdapter.isDataHolderLoadingComplete()) {
            enableRefreshing(true);
        }
    }

    @Override
    public void showLoadingProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideLoadingProgress() {
        loadingDialog.dismiss();
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
    public void showLoadMoreProgress() {
        listProductsAdapter.showLoadingItem(true);
    }

    @Override
    public void hideLoadMoreProgress() {
        listProductsAdapter.hideLoadingItem();
    }

    @Override
    public void enableLoadMore(boolean enable) {
        listProductsAdapter.enableLoadingMore(enable);
    }

    @Override
    public void enableRefreshing(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }


    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {

    }

    @Override
    public void onLoadMore() {
        getPresenter().loadMoreProducts();
    }
}
