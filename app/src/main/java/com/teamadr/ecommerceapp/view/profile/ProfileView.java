package com.teamadr.ecommerceapp.view.profile;

import com.teamadr.ecommerceapp.model.response.salesman.SalesmanDto;
import com.teamadr.ecommerceapp.model.response.customer.CustomerDto;

public interface ProfileView {
    void refreshProfileAdmin(SalesmanDto salesmanDto);
    void refreshProfileCustomer(CustomerDto customerDto);
}
