package com.teamadr.ecommerceapp.presenter.admin_profile;

import com.teamadr.ecommerceapp.model.request.admin.NewAdminDto;
import com.teamadr.ecommerceapp.presenter.BasePresenter;


public interface AdminProfilePresenter extends BasePresenter {
    void getProfileAdmin();
    void editProfileAdmin(NewAdminDto newAdminProfileDto);
}
