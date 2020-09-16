package com.teamadr.ecommerceapp.model.response.trademark;

import com.google.gson.annotations.SerializedName;

public class TrademarkDto {
    @SerializedName("id")
    private int id;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("name")
    private String name;

    public TrademarkDto(int id, String imageUrl, String name) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
