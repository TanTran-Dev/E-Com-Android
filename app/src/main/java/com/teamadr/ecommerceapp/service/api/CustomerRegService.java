package com.teamadr.ecommerceapp.service.api;

import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CustomerRegService {
    @POST("/api/auth/customers")
    Observable<ResponseBody> registrationNewCustomer(@Body NewCustomerDto newCustomerDto);
}
