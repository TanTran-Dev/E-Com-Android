package com.teamadr.ecommerceapp.presenter.product;

import android.content.Context;
import android.widget.Toast;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.event_bus.DeleteEvent;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.view.product.ProductByAdminView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;



public class ProductByAdminPresenterImpl implements ProductByAdminPresenter {
    private Context context;
    private ProductByAdminView view;
    private ProductInteractor productInteractor;

    private int pageIndex = 0;
    public ProductByAdminPresenterImpl(Context context, ProductByAdminView view) {
        this.context = context;
        this.view = view;
        this.productInteractor = new ProductInteractorImpl(context);
    }

    @Override
    public void refreshPageProductByAdmin() {
        view.showShimmerLoading();
        view.showRefreshingProgress();
        productInteractor.getProductByAdmin(null, null,
                0, RequestContansts.NUM_PAGE_SIZE,
                pageResponseBody -> {
                    Page<ProductDto> page = pageResponseBody.getData();
                    view.hideShimmerLoading();
                    view.hideRefreshingProgress();
                    view.refreshProductByAdmin(page.getItems());
                    pageIndex = 0;
                    view.enableLoadingMore(true);
                },
                throwable -> {
                    throwable.printStackTrace();
                });
    }

    @Override
    public void deleteProducts(List<Integer> productIds) {
        productInteractor.deleteProducts(productIds,
                responseBody -> {
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    refreshPageProductByAdmin();
                    EventBus.getDefault().post(new DeleteEvent(true));
                },
                throwable -> {
                    throwable.printStackTrace();
                });

    }

    @Override
    public void loadMoreProduct() {
        view.showLoadingMore(true);
        productInteractor.getProductByAdmin(null, null,
                pageIndex + 1, RequestContansts.NUM_PAGE_SIZE,
                pageResponseBody -> {
                    Page<ProductDto> page = pageResponseBody.getData();
                    view.showLoadingMore(false);
                    view.addMoreProducts(page.getItems());

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
    public void onViewDestroy() {
        productInteractor.onViewDestroy();
    }
}
