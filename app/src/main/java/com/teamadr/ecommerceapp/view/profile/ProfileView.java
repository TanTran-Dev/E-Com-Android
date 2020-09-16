package com.teamadr.ecommerceapp.view.profile;

import com.teamadr.ecommerceapp.model.response.admin.AdminDto;
import com.teamadr.ecommerceapp.model.response.customer.CustomerDto;

public interface ProfileView {
    void refreshProfileAdmin(AdminDto adminDto);
    void refreshProfileCustomer(CustomerDto customerDto);
}
