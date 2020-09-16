package com.teamadr.ecommerceapp.presenter.customer_profile;

import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.presenter.BasePresenter;


public interface CustomerProfilePresenter extends BasePresenter {
    void getProfileCustomer();
    void editProfileCustomer(NewCustomerDto newCustomerDto);
}
