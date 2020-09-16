package com.teamadr.ecommerceapp.presenter.product_detail;

import com.teamadr.ecommerceapp.model.request.comment.NewComment;
import com.teamadr.ecommerceapp.model.request.order_product.NewOrderProduct;
import com.teamadr.ecommerceapp.presenter.BasePresenter;


public interface ProductDetailPresenter extends BasePresenter {
    void refreshProductDetail(Integer productId);
    void addShoppingCartProduct();
    void createComment(NewComment newComment, int productId);
    void refreshComment(Integer productId);

    void deleteComment(String commentId, int productId);

    void loadMoreComment(Integer productId);

    void getShoppingCart(String customerId);
    void setShoppingCartId(String id);

    void setOrder(int productId, int countOption);
    void createNewOrderProduct(NewOrderProduct newOrderProduct);
}
