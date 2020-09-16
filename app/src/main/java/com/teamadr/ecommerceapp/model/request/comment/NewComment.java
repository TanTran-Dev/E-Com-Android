package com.teamadr.ecommerceapp.model.request.comment;

import com.google.gson.annotations.SerializedName;

public class NewComment {
    @SerializedName("content")
    private String content;

    @SerializedName("customerId")
    private String customerId;

    @SerializedName("productId")
    private Integer productId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
