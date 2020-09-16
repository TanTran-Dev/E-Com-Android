package com.teamadr.ecommerceapp.model.response.comment;

import com.google.gson.annotations.SerializedName;
import com.teamadr.ecommerceapp.model.response.customer.CustomerDto;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;

public class CommentDto {
    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private String content;

    @SerializedName("commentDate")
    private String commentDate;

    @SerializedName("customerDto")
    private CustomerDto customerDto;

    @SerializedName("productDto")
    private ProductDto productDto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }
}
