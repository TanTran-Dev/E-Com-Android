package com.teamadr.ecommerceapp.presenter.order_product;

import android.content.Context;
import android.widget.Toast;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.request.order_product.OrderProductDto;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.view.order_product.OrderProductView;

import java.util.List;


public class OrderProductPresenterImpl implements OrderProductPresenter {
    private Context context;
    private OrderProductView view;
    private OrderProductInteractor interactor;

    private int pageIndex = 0;

    public OrderProductPresenterImpl(Context context, OrderProductView view) {
        this.context = context;
        this.view = view;
        this.interactor = new OrderProductInteractorImpl(context);
    }

    @Override
    public void refreshOrderProducts() {
        view.showShimmerLoading();
        view.showRefreshingProgress();
        interactor.getListOrderProduct(null, null,
                0, RequestContansts.NUM_PAGE_SIZE,
                pageResponseBody -> {
                    view.hideShimmerLoading();
                    view.hideRefreshingProgress();
                    Page<OrderProductDto> page = pageResponseBody.getData();
                    view.refreshOrders(page.getItems(), page.getTotalItems());
                    pageIndex = 0;
                    view.enableLoadingMore(true);
                },
                throwable -> {
                    throwable.printStackTrace();
                });
    }

    @Override
    public void loadMoreOrderProducts() {
        view.showLoadingMore(true);
        interactor.getListOrderProduct(null, null,
                pageIndex + 1, RequestContansts.NUM_PAGE_SIZE,
                pageResponseBody -> {
                    view.hideRefreshingProgress();
                    view.showLoadingMore(false);
                    Page<OrderProductDto> page = pageResponseBody.getData();
                    view.addMoreOrderProducts(page.getItems());
                    pageIndex++;

                    if (pageIndex >= Math.ceil(page.getTotalItems() / RequestContansts.NUM_PAGE_SIZE)) {
                        view.disableLoadingMore(true);
                    } else {
                        view.disableLoadingMore(false);
                    }
                },
                throwable -> {
                    throwable.printStackTrace();
                });
    }

    @Override
    public void deleteOrderProducts(List<String> orderProductIds) {
        interactor.deleteListOrderProducts(orderProductIds,
                responseBody -> {
                    Toast.makeText(context, "Đã xoá", Toast.LENGTH_SHORT).show();
                    refreshOrderProducts();
                },
                throwable -> throwable.printStackTrace());
    }

    @Override
    public void onViewDestroy() {
        interactor.onViewDestroy();
    }
}
