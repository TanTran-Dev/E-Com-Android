package com.teamadr.ecommerceapp.model.response.auth;

import com.google.gson.annotations.SerializedName;
import com.teamadr.ecommerceapp.constants.UserType;

public class AuthenticationResult {
    @SerializedName("userID")
    private String userID;

    @SerializedName("tokenType")
    private String tokenType;

    @SerializedName("jti")
    private String jti;

    @SerializedName("target")
    private String target;

    @SerializedName("userExists")
    private boolean userExists;

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("refreshToken")
    private String refreshToken;

    @SerializedName("accessTokenExpSecs")
    private int accessTokenExpSecs;

    @SerializedName("refreshTokenExpSecs")
    private int refreshTokenExpSecs;

    @SerializedName(("userType"))
    private UserType userType;

    public AuthenticationResult() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isUserExists() {
        return userExists;
    }

    public void setUserExists(boolean userExists) {
        this.userExists = userExists;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getAccessTokenExpSecs() {
        return accessTokenExpSecs;
    }

    public void setAccessTokenExpSecs(int accessTokenExpSecs) {
        this.accessTokenExpSecs = accessTokenExpSecs;
    }

    public int getRefreshTokenExpSecs() {
        return refreshTokenExpSecs;
    }

    public void setRefreshTokenExpSecs(int refreshTokenExpSecs) {
        this.refreshTokenExpSecs = refreshTokenExpSecs;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "AuthenticationResult{" +
                "userID='" + userID + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", jti='" + jti + '\'' +
                ", target='" + target + '\'' +
                ", userExists=" + userExists +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", accessTokenExpSecs=" + accessTokenExpSecs +
                ", refreshTokenExpSecs=" + refreshTokenExpSecs +
                ", userType=" + userType +
                '}';
    }
}
