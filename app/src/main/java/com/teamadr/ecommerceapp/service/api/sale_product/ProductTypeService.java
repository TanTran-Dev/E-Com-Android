package com.teamadr.ecommerceapp.service.api.sale_product;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductTypeService {
    @GET("/api/product-type/productTypes")
    Observable<ResponseBody<Page<ProductTypeDto>>> getListProductType(@Query(RequestContansts.SORT_BY) String sortBy,
                                                                      @Query(RequestContansts.SORT_TYPE) String sortType,
                                                                      @Query(RequestContansts.PAGE_INDEX) int pageIndex,
                                                                      @Query(RequestContansts.PAGE_SIZE) int pageSize);
}
