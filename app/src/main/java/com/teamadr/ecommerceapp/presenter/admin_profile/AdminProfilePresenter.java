package com.teamadr.ecommerceapp.presenter.admin_profile;

import com.teamadr.ecommerceapp.model.request.salesman.NewSalesmanDto;
import com.teamadr.ecommerceapp.presenter.BasePresenter;


public interface AdminProfilePresenter extends BasePresenter {
    void getProfileAdmin();
    void editProfileAdmin(NewSalesmanDto newAdminProfileDto);
}
