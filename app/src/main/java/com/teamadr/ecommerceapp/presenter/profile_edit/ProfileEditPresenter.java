package com.teamadr.ecommerceapp.presenter.profile_edit;

import com.teamadr.ecommerceapp.model.request.admin.NewAdminDto;
import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.presenter.BasePresenter;

public interface ProfileEditPresenter extends BasePresenter {
    void editProfileAdmin(NewAdminDto newAdminProfileDto);
    void editProfileCustomer(NewCustomerDto newCustomerProfileDto);
}
