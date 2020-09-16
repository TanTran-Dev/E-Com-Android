package com.teamadr.ecommerceapp.service.api;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.request.admin.NewAdminDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.admin.AdminDto;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminProfileService {
    @GET("/api/auth/admins/info")
    Observable<ResponseBody<AdminDto>> getProfileAdmin(
            @Header(RequestContansts.AUTHORIZATION) String token);

    @PUT("/api/auth/admins/info/{id}")
    Observable<ResponseBody> updateProfileAdmin(@Header(RequestContansts.AUTHORIZATION) String token,
                                                @Path("id") String adminId,
                                                @Body NewAdminDto newAdminDto);
}
