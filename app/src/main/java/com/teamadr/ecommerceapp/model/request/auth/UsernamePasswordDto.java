package com.teamadr.ecommerceapp.model.request.auth;

import com.google.gson.annotations.SerializedName;

public class UsernamePasswordDto {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public UsernamePasswordDto() {
    }

    public UsernamePasswordDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UsernamePasswordDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
