package com.teamadr.ecommerceapp.presenter.list_product;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;
import com.teamadr.ecommerceapp.presenter.OnResponseAdapter;
import com.teamadr.ecommerceapp.view.list_product.ListProductView;

import java.util.Collections;

import io.reactivex.functions.Consumer;

public class ListProductPresenterImpl implements ListProductPresenter{
    private Context context;
    private ListProductView view;
    private ListProductInteractor interactor;
    private Integer productTypeId;
    private Integer trademarkId;

    private int currentPage = 0;

    public ListProductPresenterImpl(Context context, ListProductView view) {
        this.context = context;
        this.view = view;
        this.interactor = new ListProductInteractorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        interactor.onViewDestroy();
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Integer getTrademarkId() {
        return trademarkId;
    }

    public void setTrademarkId(Integer trademarkId) {
        this.trademarkId = trademarkId;
    }

    @Override
    public void refreshPage() {
        view.showRefreshingProgress();
        view.enableRefreshing(false);
        view.enableLoadMore(false);
        new Handler().postDelayed(() -> {
            view.hideRefreshingProgress();
        }, 1000);

        interactor.refreshListProduct(productTypeId, trademarkId, 0, 10,
                new OnResponseAdapter<ResponseBody<Page<ProductDto>>, ResponseBody>(context) {
                    @Override
                    public void complete(boolean success) {
                        view.enableLoadMore(true);
                        if (!success) {
                            view.refreshListProducts(Collections.emptyList());
                        }
                    }

                    @Override
                    public void success(ResponseBody<Page<ProductDto>> body) {
                        Page<ProductDto> page = body.getData();

                        if (page.getPageIndex() == page.getMaxPageIndex()){
                            view.enableLoadMore(false);
                        }else {
                            view.enableLoadMore(true);
                        }
                        currentPage = 0;

                        view.refreshListProducts(page.getItems());

                    }
                });

        interactor.refreshTrademarks(0, 50,
                new OnResponseAdapter<ResponseBody<Page<TrademarkDto>>, ResponseBody>(context) {
                    @Override
                    public void complete(boolean success) {
                        if (!success){
                            view.refreshListTrademark(Collections.emptyList());
                        }
                    }

                    @Override
                    public void success(ResponseBody<Page<TrademarkDto>> body) {
                        Page<TrademarkDto> page = body.getData();
                        view.refreshListTrademark(page.getItems());
                    }
                });

        interactor.refreshProductTypes(0, 15,
                new OnResponseAdapter<ResponseBody<Page<ProductTypeDto>>, ResponseBody>(context) {
                    @Override
                    public void complete(boolean success) {
                        if (!success){
                            view.refreshListProductType(Collections.emptyList());
                        }
                    }

                    @Override
                    public void success(ResponseBody<Page<ProductTypeDto>> body) {
                        Page<ProductTypeDto> page = body.getData();
                        view.refreshListProductType(page.getItems());
                    }
                });
    }

    @Override
    public void loadMoreProducts() {
        view.showLoadMoreProgress();
        view.enableRefreshing(false);
        interactor.refreshListProduct(productTypeId, trademarkId, currentPage + 1, 15,
                new OnResponseAdapter<ResponseBody<Page<ProductDto>>, ResponseBody>(context) {
                    @Override
                    public void complete(boolean success) {
                        view.hideLoadMoreProgress();
                        view.enableRefreshing(true);
                    }

                    @Override
                    public void success(ResponseBody<Page<ProductDto>> body) {
                        Page<ProductDto> page = body.getData();
                        if (page.getPageIndex() == page.getMaxPageIndex()) {
                            view.enableLoadMore(false);
                        }

                        currentPage ++;
                        view.addProducts(page.getItems());
                    }
                });
    }
}
