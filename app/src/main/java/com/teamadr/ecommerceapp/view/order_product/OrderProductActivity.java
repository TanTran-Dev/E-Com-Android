package com.teamadr.ecommerceapp.view.order_product;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.teamadr.ecommerceapp.adapter.recycle_view.OrderProductAdapter;
import com.teamadr.ecommerceapp.adapter.recycle_view.RecyclerViewAdapter;
import com.teamadr.ecommerceapp.model.request.order_product.OrderProductDto;
import com.teamadr.ecommerceapp.presenter.order_product.OrderProductPresenter;
import com.teamadr.ecommerceapp.presenter.order_product.OrderProductPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderProductActivity extends AppCompatActivity implements OrderProductView,
        RecyclerViewAdapter.OnItemClickListener,
        View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        SwipeRefreshLayout.OnRefreshListener, EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rcl_order_product)
    RecyclerView rclOrders;
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

    private OrderProductPresenter orderProductPresenter;
    private OrderProductAdapter orderProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            toolbarTitle.setText("Danh sách đơn hàng");
        }

        initPresenter();
        initView();

        orderProductPresenter.refreshOrderProducts();
    }

    private void initPresenter() {
        orderProductPresenter = new OrderProductPresenterImpl(this, this);
    }

    private void initView() {
        orderProductAdapter = new OrderProductAdapter(this);
        orderProductAdapter.addOnItemClickListener(this);
        orderProductAdapter.setLoadingMoreListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        rclOrders.setLayoutManager(new LinearLayoutManager(this));
        rclOrders.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rclOrders.setHasFixedSize(true);
        rclOrders.setAdapter(orderProductAdapter);

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
    public void refreshOrders(List<OrderProductDto> orderProductDtos, int totalItems) {
        orderProductAdapter.refresh(orderProductDtos);
        if (totalItems == 0){
            lnNoData.setVisibility(View.VISIBLE);
            rclOrders.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }else {
            lnNoData.setVisibility(View.GONE);
            rclOrders.setVisibility(View.VISIBLE);
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
    public void addMoreOrderProducts(List<OrderProductDto> orderProductDtos) {
        orderProductAdapter.addModels(orderProductDtos, false);
    }

    @Override
    public void disableLoadingMore(boolean disable) {
        orderProductAdapter.enableLoadingMore(!disable);
    }

    @Override
    public void enableLoadingMore(boolean enable) {
        orderProductAdapter.enableLoadingMore(enable);
    }

    @Override
    public void showLoadingMore(boolean isShow) {
        if (isShow) {
            orderProductAdapter.showLoadingItem(true);
        } else {
            orderProductAdapter.hideLoadingItem();
        }
    }

    @Override
    public void showShimmerLoading() {
        shimmerLoading.setVisibility(View.VISIBLE);
        shimmerLoading.startShimmer();
        rclOrders.setVisibility(View.GONE);
    }

    @Override
    public void hideShimmerLoading() {
        shimmerLoading.stopShimmer();
        shimmerLoading.setVisibility(View.GONE);
        rclOrders.setVisibility(View.VISIBLE);
    }


    @Override
    public void onItemClick(RecyclerView.Adapter adapter, RecyclerView.ViewHolder viewHolder,
                            int viewType, int position) {

    }

    @Override
    public void onRefresh() {
        orderProductPresenter.refreshOrderProducts();
    }

    @Override
    public void onLoadMore() {
        orderProductPresenter.loadMoreOrderProducts();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete: {
                lnConfirmDelete.setVisibility(View.VISIBLE);
                chkSelectAll.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.GONE);

                orderProductAdapter.setSelectedMode(true);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        for (int i = 0; i < orderProductAdapter.getListWrapperModels().size(); i++) {
            if (isChecked) {
                orderProductAdapter.setSelectedItem(i, true);
            } else {
                orderProductAdapter.setSelectedItem(i, false);
            }
            orderProductAdapter.notifyDataSetChanged();
        }

    }

    private void showConfirmDialog() {
        new iOSDialogBuilder(this)
                .setTitle("Xác nhận xoá!")
                .setSubtitle("Bạn chắc chắn muốn xoá sản phẩm này?")
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener("Xác nhận", dialog -> {
                    List<OrderProductDto> list =
                            orderProductAdapter.getSelectedItemModel(OrderProductDto.class);
                    List<String> orderProductIds = new ArrayList<>();

                    for (OrderProductDto orderProductDto : list) {
                        orderProductIds.add(orderProductDto.getId());
                    }

                    orderProductPresenter.deleteOrderProducts(orderProductIds);
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
        orderProductAdapter.setSelectedMode(false);
    }
}
