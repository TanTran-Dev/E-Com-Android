package com.teamadr.ecommerceapp.presenter.product_detail;

import android.content.Context;
import android.widget.Toast;

import com.teamadr.ecommerceapp.constants.RequestContansts;
import com.teamadr.ecommerceapp.model.request.comment.NewComment;
import com.teamadr.ecommerceapp.model.request.order_product.NewOrderProduct;
import com.teamadr.ecommerceapp.model.request.shopping_cart_product.NewShoppingCartProduct;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.comment.CommentDto;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.view.product_detail.ProductDetailView;

public class ProductDetailPresenterImpl implements ProductDetailPresenter {
    private Context context;
    private ProductDetailView view;
    private ProductDetailInteractor interactor;

    private int count;
    private int productId;
    private String shoppingCartId;
    private int pageIndex = 0;

    public ProductDetailPresenterImpl(Context context, ProductDetailView view) {
        this.context = context;
        this.view = view;
        this.interactor = new ProductDetailInteractorImpl(context);
    }

    @Override
    public void setOrder(int productId, int countOption) {
        this.count = countOption;
        this.productId = productId;
    }

    @Override
    public void setShoppingCartId(String id) {
        this.shoppingCartId = id;
    }

    @Override
    public void refreshProductDetail(Integer productId) {
        view.showShimmerLoading();
        interactor.getProductDetail(productId,
                productDtoResponseBody -> {
                    ProductDto productDto = productDtoResponseBody.getData();
                    view.hideShimmerLoading();
                    view.refreshProductDetail(productDto);
                },
                throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void createNewOrderProduct(NewOrderProduct newOrderProduct) {
        view.showLoadingProgress();
        interactor.createNewOrderProduct(newOrderProduct,
                responseBody -> {
                    view.hideLoadingProgress();
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                },
                throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void addShoppingCartProduct() {
        view.showLoadingProgress();
        NewShoppingCartProduct newShoppingCartProduct = new NewShoppingCartProduct();
        newShoppingCartProduct.setShoppingCartId(shoppingCartId);
        newShoppingCartProduct.setCount(count);
        newShoppingCartProduct.setProductId(productId);
        interactor.addShoppingCartProduct(newShoppingCartProduct,
                responseBody -> {
                    view.hideLoadingProgress();
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                },
                throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void createComment(NewComment newComment, int productId) {
        view.showLoadingProgress();
        interactor.createNewComment(newComment,
                responseBody -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    view.hideLoadingProgress();
                    refreshComment(productId);
                },
                Throwable::printStackTrace);
    }

    @Override
    public void refreshComment(Integer productId) {
        interactor.getPageComment(productId,
                null, null,
                0, RequestContansts.NUM_PAGE_SIZE,
                pageResponseBody -> {
                    Page<CommentDto> page = pageResponseBody.getData();
                    view.refreshComment(page.getItems());
                    pageIndex = 0;
                    view.enableLoadingMore(true);
                },
                Throwable::printStackTrace);
    }

    @Override
    public void deleteComment(String commentId, int productId) {
        view.showLoadingProgress();
        interactor.deleteComment(commentId,
                responseBody -> {
                    Toast.makeText(context, "Đã xoá bình luận", Toast.LENGTH_SHORT).show();
                    view.hideLoadingProgress();
                    refreshComment(productId);
                },
                Throwable::printStackTrace);
    }

    @Override
    public void loadMoreComment(Integer productId) {
        view.showLoadingMore(true);
        interactor.getPageComment(productId,
                null, null,
                pageIndex + 1, RequestContansts.NUM_PAGE_SIZE,
                pageResponseBody -> {
                    Page<CommentDto> page = pageResponseBody.getData();

                    view.showLoadingMore(false);
                    view.addMoreComment(page.getItems());
                    pageIndex++;

                    if (pageIndex >= Math.ceil(page.getTotalItem() / RequestContansts.NUM_PAGE_SIZE)) {
                        view.disableLoadingMore(true);
                    } else {
                        view.disableLoadingMore(false);
                    }
                },
                throwable -> {
                    throwable.printStackTrace();
                    view.showLoadingMore(false);
                });
    }

    @Override
    public void getShoppingCart(String customerId) {
        interactor.getShoppingCart(customerId,
                shoppingCartResponseBody -> view.getIdShoppingCart(shoppingCartResponseBody.getData().getId()),
                Throwable::printStackTrace);
    }


    @Override
    public void onViewDestroy() {
        interactor.onViewDestroy();
    }
}
