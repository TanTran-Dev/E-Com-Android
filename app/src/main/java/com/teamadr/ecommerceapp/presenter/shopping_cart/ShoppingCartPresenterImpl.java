package com.teamadr.ecommerceapp.presenter.shopping_cart;

import android.content.Context;
import android.widget.Toast;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.shopping_cart_product.ShoppingCartProductDto;
import com.teamadr.ecommerceapp.view.shopping_cart.ShoppingCartView;


import java.util.List;


public class ShoppingCartPresenterImpl implements ShoppingCartPresenter {
    private Context context;
    private ShoppingCartView view;
    private ShoppingCartInteractor interactor;

    private int pageIndex = 0;
    private String shoppingCartId;


    public ShoppingCartPresenterImpl(Context context, ShoppingCartView view) {
        this.context = context;
        this.view = view;
        this.interactor = new ShoppingCartInteractorImpl(context);
    }

    @Override
    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    @Override
    public void refreshShoppingCartProducts() {
        view.showShimmerLoading();
        view.showRefreshingProgress();
        interactor.refreshShoppingCartProduct(null, null,
                0, RequestContansts.NUM_PAGE_SIZE,
                pageResponseBody -> {
                    Page<ShoppingCartProductDto> page = pageResponseBody.getData();
                    view.hideShimmerLoading();
                    view.hideRefreshingProgress();
                    view.refreshShoppingCartProduct(page.getItems(), page.getTotalItems());
                    pageIndex = 0;
                    view.enableLoadingMore(true);
                },
                throwable -> {
                    throwable.printStackTrace();
                });
    }

    @Override
    public void loadMoreShoppingCartProducts() {
        view.showLoadingMore(true);
        interactor.refreshShoppingCartProduct(null, null,
                pageIndex +1, RequestContansts.NUM_PAGE_SIZE,
                pageResponseBody -> {
                    Page<ShoppingCartProductDto> page = pageResponseBody.getData();
                    view.showLoadingMore(false);
                    view.addMoreShoppingCartProduct(page.getItems());
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
    public void deleteShoppingCartProducts(List<String> shoppingCartProductIDs) {
        interactor.deleteListShoppingCartProduct(shoppingCartProductIDs,
                responseBody -> {
                    Toast.makeText(context, "Đã xoá", Toast.LENGTH_SHORT).show();
                    refreshShoppingCartProducts();
                },
                throwable -> throwable.printStackTrace());
    }

    @Override
    public void onViewDestroy() {
        interactor.onViewDestroy();
    }
}
