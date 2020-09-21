package com.teamadr.ecommerceapp.service.api;

import com.teamadr.ecommerceapp.model.request.salesman.NewSalesmanDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SalesmanRegService {
    @POST("/api/salesman/registration")
    Observable<ResponseBody> registrationNewSalesman(@Body NewSalesmanDto newSalesmanDto);
}
