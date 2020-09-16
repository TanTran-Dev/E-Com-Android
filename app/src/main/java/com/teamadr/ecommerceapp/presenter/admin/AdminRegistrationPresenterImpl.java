package com.teamadr.ecommerceapp.presenter.admin;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.teamadr.ecommerceapp.constants.Gender;
import com.teamadr.ecommerceapp.constants.UserType;
import com.teamadr.ecommerceapp.model.request.admin.NewAdminDto;
import com.teamadr.ecommerceapp.view.registration.RegistrationView;

public class AdminRegistrationPresenterImpl implements AdminRegistrationPresenter {
    private Context context;
    private RegistrationView view;
    private AdminRegistrationInteractor interactor;

    public AdminRegistrationPresenterImpl(Context context, RegistrationView view) {
        this.context = context;
        this.view = view;
        this.interactor = new AdminRegistrationInteractorImpl(context);
    }

    @Override
    public void onViewDestroy() {
        interactor.onViewDestroy();
    }

    @Override
    public void registerNewAdmin(String firstName, String lastName, String address, String birthDay, String phone, Gender gender,
                                 String username, String password, String confirmPassword, UserType userType) {
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


        NewAdminDto newAdminDto = new NewAdminDto();
        newAdminDto.setUserName(username);
        newAdminDto.setPassword(password);

        newAdminDto.setFirstName(firstName);
        newAdminDto.setLastName(lastName);
        newAdminDto.setAddress(address);
        newAdminDto.setBirthDay(birthDay);
        newAdminDto.setPhone(phone);
        newAdminDto.setGender(gender);
        newAdminDto.setUserType(userType);

        view.showLoadingProgress();
        interactor.registerNewAdminByEmail(newAdminDto,
                responseBody -> {
                    view.hideLoadingProgress();
                    view.navigateToLogin();

                    Toast.makeText(context, "Đăng ký tài khoản bán hàng thành công!", Toast.LENGTH_LONG).show();
                },
                throwable -> {
                    view.hideLoadingProgress();
                    throwable.printStackTrace();
                    Log.d("onError", "accept: " + throwable.toString());
                    Toast.makeText(context, "Đăng ký tài khoản thất bại!", Toast.LENGTH_LONG).show();
                });
    }
}
