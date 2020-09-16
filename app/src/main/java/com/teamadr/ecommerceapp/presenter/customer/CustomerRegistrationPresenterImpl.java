package com.teamadr.ecommerceapp.presenter.customer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.teamadr.ecommerceapp.constants.Gender;
import com.teamadr.ecommerceapp.constants.UserType;
import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.model.response.ResponseBody;
import com.teamadr.ecommerceapp.view.registration.RegistrationView;

import io.reactivex.functions.Consumer;

public class CustomerRegistrationPresenterImpl implements CustomerRegistrationPresenter {
    private Context context;
    private RegistrationView view;
    private CustomerRegistrationInteractor interactor;

    public CustomerRegistrationPresenterImpl(Context context, RegistrationView view) {
        this.context = context;
        this.view = view;
        this.interactor = new CustomerRegistrationInteractorImpl(context);
    }

    @Override
    public void registrationCustomer(String firstName, String lastName, String address, String birthDay,
                                     String phone, Gender gender, String username, String password,
                                     String confirmPassword, UserType userType) {

        if (firstName == null || firstName.isEmpty()){
            view.showFirstNameInputError("Nhập họ");
            return;
        }

        if (lastName == null || lastName.isEmpty()){
            view.showLastNameInputError("Nhập tên");
            return;
        }

        if (username == null || username.isEmpty()){
            view.showUsernameInputError("Nhập username");
            return;
        }

        if (password == null || password.isEmpty()){
            view.showPasswordInputError("Nhập password");
            return;
        }

        if (confirmPassword == null || confirmPassword.isEmpty()){
            view.showConfirmPasswordInputError("Nhập lại mật khẩu");
            return;
        }

        if (!confirmPassword.equals(password)){
            view.showConfirmPasswordInputError("Mật khẩu xác nhận không trùng với mật khẩu vừa nhập");
            return;
        }

        if (address == null || address.isEmpty()){
            view.showAddressInputError("Nhập địa chỉ");
            return;
        }

        if (phone == null || phone.isEmpty()){
            view.showPhoneInputError("Nhập số điện thoại");
            return;
        }

        NewCustomerDto newCustomerDto = new NewCustomerDto();
        newCustomerDto.setUserName(username);
        newCustomerDto.setPassword(password);

        newCustomerDto.setFirstName(firstName);
        newCustomerDto.setLastName(lastName);
        newCustomerDto.setAddress(address);
        newCustomerDto.setBirthDay(birthDay);
        newCustomerDto.setPhone(phone);
        newCustomerDto.setGender(gender);
        newCustomerDto.setUserType(userType);

        view.showLoadingProgress();
        interactor.registrationNewCustomer(newCustomerDto,
                new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        view.hideLoadingProgress();
                        view.navigateToLogin();

                        Toast.makeText(context, "Đăng ký tài khoản mua hàng thành công!", Toast.LENGTH_LONG).show();
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        Log.d("onError", "accept: " + throwable.toString());
                        Toast.makeText(context, "Đăng ký thất bại", Toast.LENGTH_LONG).show();
                        view.hideLoadingProgress();
                    }
                });
    }
}
