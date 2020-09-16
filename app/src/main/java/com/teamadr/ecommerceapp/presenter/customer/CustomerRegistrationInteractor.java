package com.teamadr.ecommerceapp.presenter.customer;

import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import io.reactivex.functions.Consumer;

public interface CustomerRegistrationInteractor extends BaseInteractor {
    void registrationNewCustomer(NewCustomerDto newCustomerDto,
                                 Consumer<ResponseBody> onSuccess,
                                 Consumer<Throwable> onError);
}
