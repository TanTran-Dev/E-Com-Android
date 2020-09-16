package com.teamadr.ecommerceapp.presenter.product;


import android.content.Context;
import android.util.Log;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.view.product.ProductView;

import java.util.List;


public class ProductPresenterImpl implements ProductPresenter {
    private Context context;
    private ProductView view;
    private ProductInteractor productInteractor;
    private List<String> sortBy;
    private List<String> sortType;
    private Integer trademarkId;
    private int pageIndex = 0;

    @Override
    public void setTrademarkId(Integer trademarkId) {
        this.trademarkId = trademarkId;
    }

    public ProductPresenterImpl(Context context, ProductView view) {
        this.context = context;
        this.view = view;
        this.productInteractor = new ProductInteractorImpl(context);
    }

    @Override
    public void setOrder(List<String> sortBy, List<String> sortType, Integer trademarkId) {
        this.sortBy = sortBy;
        this.sortType = sortType;
        this.trademarkId = trademarkId;
    }

    @Override
    public void refreshProductDto(Integer productTypeId) {
        view.showShimmerLoading();
        view.showRefreshingProgress();
        productInteractor.refreshListProduct(productTypeId, trademarkId, sortBy, sortType,
                0, RequestContansts.NUM_PAGE_SIZE,
                pageDtoResponseBody -> {
                    Log.i("productDto", pageDtoResponseBody.getData() + "");
                    Page<ProductDto> page = pageDtoResponseBody.getData();
                    view.hideShimmerLoading();
                    view.hideRefreshingProgress();

                    view.refreshProductDto(page.getItems(), page.getTotalItem());
                    pageIndex = 0;
                    view.enableLoadingMore(true);
                },
                Throwable::printStackTrace);
    }

    @Override
    public void loadMoreProduct(Integer productTypeId) {
        view.showLoadingMore(true);
        productInteractor.refreshListProduct(productTypeId, trademarkId, sortBy, sortType,
                pageIndex + 1, RequestContansts.NUM_PAGE_SIZE,
                pageDtoResponseBody -> {
                    Log.i("productDto", pageDtoResponseBody.getData() + "");
                    Page<ProductDto> page = pageDtoResponseBody.getData();
                    view.showLoadingMore(false);
                    view.addMoreProducts(page.getItems());
                    pageIndex++;

                    if (pageIndex >= Math.ceil(page.getTotalItem() / RequestContansts.NUM_PAGE_SIZE)) {
                        view.disableLoadingMore(true);
                    } else {
                        view.disableLoadingMore(false);
                    }
                },
                throwable -> {
                    throwable.printStackTrace();
                    view.showLoadingMore(false);
                });
    }

    @Override
    public void onViewDestroy() {
        productInteractor.onViewDestroy();
    }
}
