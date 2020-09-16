package com.teamadr.ecommerceapp.view.registration;

public interface RegistrationView {
    void showLoadingProgress();
    void hideLoadingProgress();

    void navigateToLogin();

    void showLastNameInputError(String message);

    void showFirstNameInputError(String message);

    void showUsernameInputError(String message);

    void showPasswordInputError(String message);

    void showConfirmPasswordInputError(String message);

    void showPhoneInputError(String message);

    void showAddressInputError(String message);
}
