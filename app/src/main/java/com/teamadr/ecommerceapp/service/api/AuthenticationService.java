package com.teamadr.ecommerceapp.service.api;

import com.teamadr.ecommerceapp.model.request.auth.UsernamePasswordDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.auth.AuthenticationResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthenticationService {
    @POST("/api/authentication/username-password")
    Observable<ResponseBody<AuthenticationResult>> getAuth(
            @Header("client_id") String client_id,
            @Header("secret") String secret,
            @Body UsernamePasswordDto usernamePasswordDto
    );
}
