package com.teamadr.ecommerceapp.presenter.shopping_cart;

import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.shopping_cart_product.ShoppingCartProductDto;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface ShoppingCartInteractor extends BaseInteractor {
    void refreshShoppingCartProduct(List<String> sortBy, List<String> sortType,
                                    int pageIndex, int pageSize,
                                    Consumer<ResponseBody<Page<ShoppingCartProductDto>>> onSuccess,
                                    Consumer<Throwable> onError);
    void deleteListShoppingCartProduct(List<String> shoppingCartProductIDs,
                                       Consumer<ResponseBody> onSuccess,
                                       Consumer<Throwable> onError);
}
