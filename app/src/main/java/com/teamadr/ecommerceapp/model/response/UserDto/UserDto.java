package com.teamadr.ecommerceapp.model.response.UserDto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDto implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("username")
    private String username;
    @SerializedName("banned")
    private boolean banned;
    @SerializedName("isBanned")
    private boolean isBanned;
    @SerializedName("lastActive")
    private String lastActive;

    public UserDto() {
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getLastActive() {
        return lastActive;
    }

    public void setLastActive(String lastActive) {
        this.lastActive = lastActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
