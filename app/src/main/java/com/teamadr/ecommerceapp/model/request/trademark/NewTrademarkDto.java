package com.teamadr.ecommerceapp.model.request.trademark;

import com.google.gson.annotations.SerializedName;

public class NewTrademarkDto {
    @SerializedName("name")
    private String mame;

    @SerializedName("imageUrl")
    private String imageUrl;

    public String getMame() {
        return mame;
    }

    public void setMame(String mame) {
        this.mame = mame;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
