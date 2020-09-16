package com.teamadr.ecommerceapp.service.api;

import com.teamadr.ecommerceapp.model.request.admin.NewAdminDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AdminRegService {
    @POST("/api/admins/registration")
    Observable<ResponseBody> registrationNewAdmin(@Body NewAdminDto newAdminDto);
}
