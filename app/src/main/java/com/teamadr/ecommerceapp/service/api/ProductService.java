package com.teamadr.ecommerceapp.service.api;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.request.product.NewProduct;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    @GET("/api/product/products")
    Observable<ResponseBody<Page<ProductDto>>> getListProducts(
            @Query("productTypeID") Integer productTypeId,
            @Query("trademarkID") Integer trademarkId,
            @Query(RequestContansts.SORT_BY) List<String> sortBy,
            @Query(RequestContansts.SORT_TYPE) List<String> sortType,
            @Query(RequestContansts.PAGE_INDEX) int pageIndex,
            @Query(RequestContansts.PAGE_SIZE) int pageSize);

    @GET("/api/product/product/{id}")
    Observable<ResponseBody<ProductDto>> getProductDetail(@Path("id") Integer productId);

    @POST("/api/product/newProduct")
    Observable<ResponseBody> createNewProduct(@Header(RequestContansts.AUTHORIZATION) String token,
                                              @Body NewProduct newProduct);

    @HTTP(method = "DELETE", path = "/api/product/products", hasBody = true)
    Observable<ResponseBody> deleteProducts(@Header(RequestContansts.AUTHORIZATION) String token,
                                            @Body List<Integer> productIds);

    @GET("/api/product/products/{adminId}")
    Observable<ResponseBody<Page<ProductDto>>> getPageProductByAdmin(
            @Query("adminId") String adminId,
            @Query(RequestContansts.SORT_BY) List<String> sortBy,
            @Query(RequestContansts.SORT_TYPE) List<String> sortType,
            @Query(RequestContansts.PAGE_INDEX) int pageIndex,
            @Query(RequestContansts.PAGE_SIZE) int pageSize);
}
