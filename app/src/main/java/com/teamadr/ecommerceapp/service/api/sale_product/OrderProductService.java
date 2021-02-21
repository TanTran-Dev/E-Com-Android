package com.teamadr.ecommerceapp.service.api.sale_product;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.request.order_product.NewOrderProduct;
import com.teamadr.ecommerceapp.model.request.order_product.OrderProductDto;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderProductService {
    @POST("/api/order-product/new-order")
    Observable<ResponseBody> createNewOrder(@Body NewOrderProduct newOrderProduct);

    @GET("/api/order-product/order-products")
    Observable<ResponseBody<Page<OrderProductDto>>> getListOrderProduct(
            @Query("customerId") String customerId,
            @Query(RequestContansts.SORT_BY) List<String> sortBy,
            @Query(RequestContansts.SORT_TYPE) List<String> sortType,
            @Query(RequestContansts.PAGE_INDEX) int pageIndex,
            @Query(RequestContansts.PAGE_SIZE) int pageSize);

    @HTTP(method = "DELETE", path = "/api/order-product/order-products", hasBody = true)
    Observable<ResponseBody> deleteListOrderProducts(@Body List<String> orderProductIds);
}
