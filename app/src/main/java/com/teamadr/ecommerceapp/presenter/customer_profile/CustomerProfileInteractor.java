package com.teamadr.ecommerceapp.presenter.customer_profile;

import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.model.response.customer.CustomerDto;
import com.teamadr.ecommerceapp.presenter.BaseInteractor;

import io.reactivex.functions.Consumer;

public interface CustomerProfileInteractor extends BaseInteractor {
    void getProfileCustomer(String token,
                            Consumer<ResponseBody<CustomerDto>> onSuccess,
                            Consumer<Throwable> onError);
    void updateProfileCustomer(String customerId, NewCustomerDto newCustomerDto,
                               Consumer<ResponseBody> onSuccess,
                               Consumer<Throwable> onError);
}
