package com.teamadr.ecommerceapp.presenter.salesman;

import com.teamadr.ecommerceapp.constants.Gender;
import com.teamadr.ecommerceapp.constants.UserType;
import com.teamadr.ecommerceapp.presenter.BasePresenter;


public interface SalesmanRegistrationPresenter extends BasePresenter {
    void registerNewAdmin(String firstName, String lastName, String address, String birthDay, String phone, Gender gender,
                          String username, String password, String confirmPassword, UserType userType);
}
