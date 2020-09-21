package com.teamadr.ecommerceapp.presenter.profile_edit;

import com.teamadr.ecommerceapp.model.request.salesman.NewSalesmanDto;
import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import io.reactivex.functions.Consumer;

public interface ProfileEditInteractor extends BaseInteractor {
    void updateProfileAdmin(String adminId, NewSalesmanDto newSalesmanDto,
                            Consumer<ResponseBody> onSuccess,
                            Consumer<Throwable> onError);

    void updateProfileCustomer(String customerId, NewCustomerDto newCustomerDto,
                               Consumer<ResponseBody> onSuccess,
                               Consumer<Throwable> onError);
}
