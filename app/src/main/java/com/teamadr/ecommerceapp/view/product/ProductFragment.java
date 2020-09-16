package com.teamadr.ecommerceapp.view.product;


import android.content.Context;
import android.content.Intent;
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

import com.facebook.shimmer.ShimmerFrameLayout;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.adapter.recycle_view.EndlessLoadingRecyclerViewAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.ProductAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.RecyclerViewAdapter;
import com.teamadr.ecommerceapp.constants.StringConstant;
import com.teamadr.ecommerceapp.custom_view.LoadingDialog;
import com.teamadr.ecommerceapp.event_bus.DeleteEvent;
import com.teamadr.ecommerceapp.event_bus.NewProductEvent;
import com.teamadr.ecommerceapp.event_bus.SortEvent;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.presenter.product.ProductPresenter;
import com.teamadr.ecommerceapp.presenter.product.ProductPresenterImpl;
import com.teamadr.ecommerceapp.view.product_detail.ProductDetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductFragment extends Fragment implements ProductView, RecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rcl_product)
    RecyclerView rclProduct;
    @BindView(R.id.ln_no_data)
    LinearLayout lnNoData;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerLoading;

    private ProductPresenter productPresenter;
    private int productTypeId;
    private ProductAdapter productAdapter;
    private LoadingDialog loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        loadingDialog = new LoadingDialog(getContext());
        productTypeId = getArguments().getInt(StringConstant.KEY_PRODUCT_TYPE_ID, -1);
        initView();
        productPresenter.refreshProductDto(productTypeId);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
     EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SortEvent sortEvent) {
        productPresenter.setOrder(sortEvent.getSortBy(), sortEvent.getSortType(), sortEvent.getTrademarkId());
        productPresenter.setTrademarkId(sortEvent.getTrademarkId());
        productPresenter.refreshProductDto(productTypeId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NewProductEvent newProductEvent){
        productPresenter.refreshProductDto(newProductEvent.getNewProduct().getProductTypeId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DeleteEvent deleteEvent){
        if (deleteEvent.isDelete()){
            productPresenter.refreshProductDto(productTypeId);
        }
    }

    private void initPresenter() {
        productPresenter = new ProductPresenterImpl(getContext(), this);
    }

    private void initView() {
        productAdapter = new ProductAdapter(getContext());
        productAdapter.setLoadingMoreListener(this);
        productAdapter.addOnItemClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(this);

        rclProduct.setLayoutManager(new LinearLayoutManager(getContext()));

        rclProduct.setHasFixedSize(true);
        rclProduct.setAdapter(productAdapter);
    }

    @Override
    public void refreshProductDto(List<ProductDto> productDtos, int totalItems) {
        productAdapter.refresh(productDtos);
        if (totalItems == 0) {
            lnNoData.setVisibility(View.VISIBLE);
            rclProduct.setVisibility(View.GONE);
        } else {
            lnNoData.setVisibility(View.GONE);
            rclProduct.setVisibility(View.VISIBLE);
        }
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
        loadingDialog.show();
    }

    @Override
    public void hideLoadingProgress() {
        loadingDialog.dismiss();
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
    public void onRefresh() {
        productPresenter.refreshProductDto(productTypeId);
    }

    @Override
    public void onLoadMore() {
        productPresenter.loadMoreProduct(productTypeId);
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder, int viewType, int position) {
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        ProductDto productDto = productAdapter.getItem(position, ProductDto.class);
        intent.putExtra(StringConstant.KEY_PRODUCT_ID, productDto.getId());
        startActivity(intent);
    }
}
