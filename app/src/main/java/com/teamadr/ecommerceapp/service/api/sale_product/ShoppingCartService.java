package com.teamadr.ecommerceapp.service.api.sale_product;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.request.shopping_cart_product.NewShoppingCartProduct;
import com.teamadr.ecommerceapp.model.request.shopping_cart.ShoppingCart;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.shopping_cart_product.ShoppingCartProductDto;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShoppingCartService {
    @GET("/api/shopping-cart/shopping-cart")
    Observable<ResponseBody<ShoppingCart>> getShoppingCart(@Query("customerId") String customerId);

    @POST("/api/shopping-cart-product/new-shopping-cart-product")
    Observable<ResponseBody> addProductToShoppingCart(@Body NewShoppingCartProduct newShoppingCartProduct);

    @GET("/api/shopping-cart-product/shopping-cart-products")
    Observable<ResponseBody<Page<ShoppingCartProductDto>>> refreshListShoppingCartProduct(
            @Query("customerId") String customerId,
            @Query(RequestContansts.SORT_BY) List<String> sortBy,
            @Query(RequestContansts.SORT_TYPE) List<String> sortType,
            @Query(RequestContansts.PAGE_INDEX) int pageIndex,
            @Query(RequestContansts.PAGE_SIZE) int pageSize);

    @HTTP(method = "DELETE", path = "/api/shopping-cart-product/shopping-cart-products", hasBody = true)
    Observable<ResponseBody> deleteListShoppingCartProduct(@Body List<String> shoppingCartProductIDs);
}
