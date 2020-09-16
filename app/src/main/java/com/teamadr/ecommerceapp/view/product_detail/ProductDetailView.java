package com.teamadr.ecommerceapp.view.product_detail;


import com.teamadr.ecommerceapp.model.response.comment.CommentDto;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;

import java.util.List;


public interface ProductDetailView {
    void refreshProductDetail(ProductDto productDto);
    void showLoadingProgress();
    void hideLoadingProgress();

    void getIdShoppingCart(String shoppingCartId);
    void refreshComment(List<CommentDto> list);

    void addMoreComment(List<CommentDto> list);
    void showLoadingMore(boolean isShow);
    void disableLoadingMore(boolean disable);
    void enableLoadingMore(boolean enable);
    void showShimmerLoading();
    void hideShimmerLoading();
}
