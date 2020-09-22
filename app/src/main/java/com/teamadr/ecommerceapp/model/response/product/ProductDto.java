package com.teamadr.ecommerceapp.model.response.product;

import com.google.gson.annotations.SerializedName;
import com.teamadr.ecommerceapp.model.response.salesman.SalesmanDto;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;

public class ProductDto {
    @SerializedName("id")
    private int id;

    @SerializedName("sale")
    private boolean sale;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private int price;

    @SerializedName("count")
    private int count;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("bigImageUrl")
    private String bigImageUrl;

    @SerializedName("smallImageUrl")
    private String smallImageUrl;

    @SerializedName("productTypeDto")
    private ProductTypeDto productTypeDto;

    @SerializedName("trademarkDto")
    private TrademarkDto trademarkDto;

    @SerializedName("salesmanDto")
    private SalesmanDto salesmanDto;

    @SerializedName("information")
    private String information;

    public ProductDto() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public ProductTypeDto getProductTypeDto() {
        return productTypeDto;
    }

    public void setProductTypeDto(ProductTypeDto productTypeDto) {
        this.productTypeDto = productTypeDto;
    }

    public TrademarkDto getTrademarkDto() {
        return trademarkDto;
    }

    public void setTrademarkDto(TrademarkDto trademarkDto) {
        this.trademarkDto = trademarkDto;
    }

    public SalesmanDto getSalesmanDto() {
        return salesmanDto;
    }

    public void setSalesmanDto(SalesmanDto salesmanDto) {
        this.salesmanDto = salesmanDto;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
