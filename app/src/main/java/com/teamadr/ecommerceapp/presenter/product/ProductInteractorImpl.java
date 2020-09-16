package com.teamadr.ecommerceapp.presenter.product;

import android.content.Context;

import com.teamadr.ecommerceapp.model.request.product.NewProduct;
import com.teamadr.ecommerceapp.model.response.Page;
import com.teamadr.ecommerceapp.model.response.product.ProductDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.product_type.ProductTypeDto;
import com.teamadr.ecommerceapp.model.response.trademark.TrademarkDto;
import com.teamadr.ecommerceapp.service.api.ProductService;
import com.teamadr.ecommerceapp.service.api.ProductTypeService;
import com.teamadr.ecommerceapp.service.api.TrademarkService;
import com.teamadr.ecommerceapp.service.api_client.APIClient;
import com.teamadr.ecommerceapp.utils.UserAuth;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProductInteractorImpl implements ProductInteractor {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public ProductInteractorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void refreshListProduct(Integer productTypeId, Integer trademarkId,
                                   List<String> sortBy, List<String> sortType,
                                   int pageIndex, int pageSize,
                                   Consumer<ResponseBody<Page<ProductDto>>> onSuccess,
                                   Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(ProductService.class)
                .getListProducts(productTypeId, trademarkId, sortBy, sortType, pageIndex, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }


    @Override
    public void addNewProduct(NewProduct newProduct,
                              Consumer<ResponseBody> onSuccess, Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(ProductService.class)
                .createNewProduct(UserAuth.getBearerToken(context), newProduct)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }


    @Override
    public void refreshTrademarks(List<String> sortBy, List<String> sortType, int pageIndex, int pageSize,
                                  Consumer<ResponseBody<Page<TrademarkDto>>> onSuccess,
                                  Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(TrademarkService.class)
                .getTrademarks(sortBy, sortType, pageIndex, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void refreshProductTypes(String sortBy, String sortType, int pageIndex, int pageSize,
                                    Consumer<ResponseBody<Page<ProductTypeDto>>> onSuccess,
                                    Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(ProductTypeService.class)
                .getListProductType(sortBy, sortType, pageIndex, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void deleteProducts(List<Integer> productIds,
                               Consumer<ResponseBody> onSuccess,
                               Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(ProductService.class)
                .deleteProducts(UserAuth.getBearerToken(context), productIds)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(onSuccess, onError);
        compositeDisposable.add(disposable);
    }

    @Override
    public void getProductByAdmin(List<String> sortBy, List<String> sortType,
                                  int pageIndex, int pageSize,
                                  Consumer<ResponseBody<Page<ProductDto>>> onSuccess,
                                  Consumer<Throwable> onError) {
        Disposable disposable = APIClient.getInstance()
                .create(ProductService.class)
                .getPageProductByAdmin(UserAuth.getUserId(context),
                        sortBy, sortType, pageIndex, pageSize)
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
