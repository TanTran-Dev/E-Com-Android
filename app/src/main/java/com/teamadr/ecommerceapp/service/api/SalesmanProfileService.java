package com.teamadr.ecommerceapp.service.api;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.request.salesman.NewSalesmanDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.salesman.SalesmanDto;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SalesmanProfileService {
    @GET("/api/auth/salesman/info")
    Observable<ResponseBody<SalesmanDto>> getProfileSalesman(
            @Header(RequestContansts.AUTHORIZATION) String token);

    @PUT("/api/auth/salesman/info/{id}")
    Observable<ResponseBody> updateProfileSalesman(@Header(RequestContansts.AUTHORIZATION) String token,
                                                   @Path("id") String salesmanId,
                                                   @Body NewSalesmanDto newSalesmanDto);
}
