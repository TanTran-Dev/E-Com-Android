package com.teamadr.ecommerceapp.presenter.customer;

import com.teamadr.ecommerceapp.constants.Gender;
import com.teamadr.ecommerceapp.constants.UserType;

public interface CustomerRegistrationPresenter {
    void registrationCustomer(String firstName, String lastName, String address, String birthDay, String phone, Gender gender,
                              String username, String password, String confirmPassword, UserType userType);
}
