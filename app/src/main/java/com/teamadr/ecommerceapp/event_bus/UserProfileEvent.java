package com.teamadr.ecommerceapp.event_bus;

public class UserProfileEvent {
    private String avatarUrl;
    private String coverUrl;
    private String name;
    private String address;

    public UserProfileEvent() {
    }

    public UserProfileEvent(String avatarUrl, String coverUrl, String name, String address) {
        this.avatarUrl = avatarUrl;
        this.coverUrl = coverUrl;
        this.name = name;
        this.address = address;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
