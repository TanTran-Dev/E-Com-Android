package com.teamadr.ecommerceapp.service.api.sale_product;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrademarkService {
    @GET("/api/trademark/trademarks")
    Observable<ResponseBody<Page<TrademarkDto>>> getTrademarks(
            @Query(RequestContansts.SORT_BY) List<String> sortBy,
            @Query(RequestContansts.SORT_TYPE) List<String> sortType,
            @Query(RequestContansts.PAGE_INDEX) int pageIndex,
            @Query(RequestContansts.PAGE_SIZE) int pageSize);
}
