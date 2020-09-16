package com.teamadr.ecommerceapp.model.request.product;

import com.google.gson.annotations.SerializedName;

public class NewProduct {
    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private Integer price;

    @SerializedName("bigImageUrl")
    private String bigImageUrl;

    @SerializedName("smallImageUrl")
    private String smallImageUrl;

    @SerializedName("sale")
    private Boolean sale;

    @SerializedName("count")
    private Integer count;

    @SerializedName("adminId")
    private String adminId;

    @SerializedName("productTypeId")
    private Integer productTypeId;

    @SerializedName("trademarkId")
    private Integer trademarkId;

    @SerializedName("information")
    private String information;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBigImageUrl() {
        return bigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        this.bigImageUrl = bigImageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public Boolean getSale() {
        return sale;
    }

    public void setSale(Boolean sale) {
        this.sale = sale;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Integer getTrademarkId() {
        return trademarkId;
    }

    public void setTrademarkId(Integer trademarkId) {
        this.trademarkId = trademarkId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
