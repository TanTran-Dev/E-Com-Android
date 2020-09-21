package com.teamadr.ecommerceapp.presenter.salesman;

import com.teamadr.ecommerceapp.model.request.salesman.NewSalesmanDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import io.reactivex.functions.Consumer;

public interface SalesmanRegistrationInteractor extends BaseInteractor {
    void registerNewAdminByEmail(NewSalesmanDto newSalesmanDto,
                                 Consumer<ResponseBody> onSuccess,
                                 Consumer<Throwable> onError);
}
