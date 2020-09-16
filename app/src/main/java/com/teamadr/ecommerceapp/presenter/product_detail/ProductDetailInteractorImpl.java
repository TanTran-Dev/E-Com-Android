package com.teamadr.ecommerceapp.presenter.product_detail;

import android.content.Context;

import com.teamadr.ecommerceapp.model.request.comment.NewComment;
import com.teamadr.ecommerceapp.model.request.order_product.NewOrderProduct;
import com.teamadr.ecommerceapp.model.request.shopping_cart.ShoppingCart;
import com.teamadr.ecommerceapp.model.request.shopping_cart_product.NewShoppingCartProduct;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.comment.CommentDto;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.service.api.CommentService;
import com.teamadr.ecommerceapp.service.api.OrderProductService;
import com.teamadr.ecommerceapp.service.api.ProductService;
import com.teamadr.ecommerceapp.service.api.ShoppingCartService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;
import com.teamadr.ecommerceapp.utils.UserAuth;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailInteractorImpl implements ProductDetailInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ProductDetailInteractorImpl(Context context) {
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getProductDetail(Integer productId,
                                 Consumer<ResponseBody<ProductDto>> onSuccess,
                                 Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(ProductService.class)
                .getProductDetail(productId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void addShoppingCartProduct(NewShoppingCartProduct newShoppingCartProduct,
                                       Consumer<ResponseBody> onSuccess,
                                       Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(ShoppingCartService.class)
                .addProductToShoppingCart(newShoppingCartProduct)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void createNewOrderProduct(NewOrderProduct newOrderProduct,
                                      Consumer<ResponseBody> onSuccess,
                                      Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(OrderProductService.class)
                .createNewOrder(newOrderProduct)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void getPageComment(Integer productId, List<String> sortBy, List<String> sortType,
                               int pageIndex, int pageSize,
                               Consumer<ResponseBody<Page<CommentDto>>> onSuccess,
                               Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(CommentService.class)
                .getPageComments(productId, sortBy, sortType, pageIndex, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void createNewComment(NewComment newComment,
                                 Consumer<ResponseBody> onSuccess,
                                 Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(CommentService.class)
                .createNewComment(UserAuth.getBearerToken(context), newComment)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void deleteComment(String commentId,
                              Consumer<ResponseBody> onSuccess,
                              Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(CommentService.class)
                .deleteComment(UserAuth.getBearerToken(context), commentId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void getShoppingCart(String customerId,
                                Consumer<ResponseBody<ShoppingCart>> onSuccess,
                                Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(ShoppingCartService.class)
                .getShoppingCart(customerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        compositeDisposable.clear();
    }
}
