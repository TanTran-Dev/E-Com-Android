package com.teamadr.ecommerceapp.presenter.profile_edit;

import com.teamadr.ecommerceapp.model.request.salesman.NewSalesmanDto;
import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.presenter.BasePresenter;

public interface ProfileEditPresenter extends BasePresenter {
    void editProfileAdmin(NewSalesmanDto newAdminProfileDto);
    void editProfileCustomer(NewCustomerDto newCustomerProfileDto);
}
