package com.teamadr.ecommerceapp.presenter.product_detail;

import com.teamadr.ecommerceapp.model.request.comment.NewComment;
import com.teamadr.ecommerceapp.model.request.order_product.NewOrderProduct;
import com.teamadr.ecommerceapp.model.request.shopping_cart.ShoppingCart;
import com.teamadr.ecommerceapp.model.request.shopping_cart_product.NewShoppingCartProduct;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.comment.CommentDto;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface ProductDetailInteractor extends BaseInteractor {
    void getProductDetail(Integer productId,
                          Consumer<ResponseBody<ProductDto>> onSuccess,
                          Consumer<Throwable> onError);

    void addShoppingCartProduct(NewShoppingCartProduct newShoppingCartProduct,
                                Consumer<ResponseBody> onSuccess,
                                Consumer<Throwable> onError);

    void getShoppingCart(String customerId,
                         Consumer<ResponseBody<ShoppingCart>> onSuccess,
                         Consumer<Throwable> onError);

    void createNewOrderProduct(NewOrderProduct newOrderProduct,
                               Consumer<ResponseBody> onSuccess,
                               Consumer<Throwable> onError);

    void getPageComment(Integer productId, List<String> sortBy, List<String> sortType, int pageIndex, int pageSize,
                        Consumer<ResponseBody<Page<CommentDto>>> onSuccess,
                        Consumer<Throwable> onError);

    void createNewComment(NewComment newComment,
                          Consumer<ResponseBody> onSuccess,
                          Consumer<Throwable> onError);

    void deleteComment(String commentId,
                       Consumer<ResponseBody> onSuccess,
                       Consumer<Throwable> onError);
}
