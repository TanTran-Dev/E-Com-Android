package com.teamadr.ecommerceapp.service.api.customer;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.customer.CustomerDto;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerProfileService {
    @GET("/api/auth/customers/info")
    Observable<ResponseBody<CustomerDto>> getProfileCustomer(@Header(RequestContansts.AUTHORIZATION)
                                                                     String token);

    @PUT("/api/auth/customers/info/{id}")
    Observable<ResponseBody> updateProfileCustomer(@Header(RequestContansts.AUTHORIZATION) String token,
                                                   @Path("id") String customerId,
                                                   @Body NewCustomerDto newCustomerDto);
}
