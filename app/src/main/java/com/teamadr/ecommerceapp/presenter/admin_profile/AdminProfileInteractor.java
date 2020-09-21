package com.teamadr.ecommerceapp.presenter.admin_profile;

import com.teamadr.ecommerceapp.model.request.salesman.NewSalesmanDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.salesman.SalesmanDto;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import io.reactivex.functions.Consumer;

public interface AdminProfileInteractor extends BaseInteractor {
    void getProfileAdmin(String token,
                         Consumer<ResponseBody<SalesmanDto>> onSuccess,
                         Consumer<Throwable> onError);
    void updateProfileAdmin(String adminId, NewSalesmanDto newSalesmanDto,
                            Consumer<ResponseBody> onSuccess,
                            Consumer<Throwable> onError);
}
