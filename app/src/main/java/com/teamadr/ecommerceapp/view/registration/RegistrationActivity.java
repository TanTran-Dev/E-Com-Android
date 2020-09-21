package com.teamadr.ecommerceapp.view.registration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.constants.Gender;
import com.teamadr.ecommerceapp.constants.StringConstant;
import com.teamadr.ecommerceapp.constants.UserType;
import com.teamadr.ecommerceapp.custom_view.LoadingDialog;
import com.teamadr.ecommerceapp.presenter.salesman.SalesmanRegistrationPresenter;
import com.teamadr.ecommerceapp.presenter.salesman.SalesmanRegistrationPresenterImpl;
import com.teamadr.ecommerceapp.presenter.customer.CustomerRegistrationPresenter;
import com.teamadr.ecommerceapp.presenter.customer.CustomerRegistrationPresenterImpl;
import com.teamadr.ecommerceapp.view.login.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView, View.OnClickListener {
    @BindView(R.id.radioMale)
    RadioButton radioMale;
    @BindView(R.id.radioFemale)
    RadioButton radioFemale;
    @BindView(R.id.radioCustomer)
    RadioButton radioCustomer;
    @BindView(R.id.radioAdmin)
    RadioButton radioAdmin;
    @BindView(R.id.txtDateOfBirth)
    TextInputEditText txtDateOfBirth;
    @BindView(R.id.edtFirstName)
    EditText edtFirstName;
    @BindView(R.id.edtLastName)
    EditText edtLastName;
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    @BindView(R.id.edtAddress)
    EditText edtAddress;
    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @BindView(R.id.txtFirstNameValidate)
    TextView txtFirstNameValidate;
    @BindView(R.id.txtLastNameValidate)
    TextView txtLastNameValidate;
    @BindView(R.id.txtUsernameValidate)
    TextView txtUsernameValidate;
    @BindView(R.id.txtPasswordValidate)
    TextView txtPasswordValidate;
    @BindView(R.id.txtConfirmPasswordValidate)
    TextView txtConfirmPasswordValidate;
    @BindView(R.id.txtPhoneValidate)
    TextView txtPhoneValidate;
    @BindView(R.id.txtAddressValidate)
    TextView txtAddressValidate;

    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private LoadingDialog loadingDialog;
    private SalesmanRegistrationPresenter salesmanRegistrationPresenter;
    private CustomerRegistrationPresenter customerRegistrationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Đăng ký tài khoản");
        }

        initPresenter();
        initView();
        intiView();
    }

    private void initPresenter() {
        salesmanRegistrationPresenter = new SalesmanRegistrationPresenterImpl(this, this);
        customerRegistrationPresenter = new CustomerRegistrationPresenterImpl(this, this);
    }

    private void initView() {
        loadingDialog = new LoadingDialog(this);

        txtFirstNameValidate.setVisibility(View.GONE);
        txtLastNameValidate.setVisibility(View.GONE);
        txtUsernameValidate.setVisibility(View.GONE);
        txtConfirmPasswordValidate.setVisibility(View.GONE);
        txtConfirmPasswordValidate.setVisibility(View.GONE);
        txtAddressValidate.setVisibility(View.GONE);
        txtPhoneValidate.setVisibility(View.GONE);
    }

    private void intiView() {
        btnRegister.setOnClickListener(this);
        txtDateOfBirth.setOnClickListener(this);
    }

    private Gender getUserGender() {
        if (radioMale.isChecked()) {
            return Gender.MALE;
        } else {
            return Gender.FEMALE;
        }
    }

    private Boolean checkUser() {
        if (radioAdmin.isChecked()) {
            return true;
        }

        if (radioCustomer.isChecked()) {
            return false;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister: {
                String firstName = edtFirstName.getText().toString().trim();
                String lastName = edtLastName.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();
                String birthDay = txtDateOfBirth.getText().toString();
                String phone = edtPhoneNumber.getText().toString().trim();
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();

                if (username.matches(emailPattern)){
                    txtUsernameValidate.setVisibility(View.GONE);
                }else {
                    txtUsernameValidate.setVisibility(View.VISIBLE);
                    txtUsernameValidate.setText("Sai định dạng Email!!");
                }

                UserType userType = null;
                if (radioAdmin.isChecked()){
                    userType = UserType.SALESMAN;
                }

                if (radioCustomer.isChecked()){
                    userType = UserType.CUSTOMER;
                }
                if (checkUser()) {
                    salesmanRegistrationPresenter.registerNewAdmin(
                            firstName, lastName, address, birthDay, phone, getUserGender(),
                            username, password, confirmPassword, userType);
                } else {
                    customerRegistrationPresenter.registrationCustomer(
                            firstName, lastName, address, birthDay, phone, getUserGender(),
                            username, password, confirmPassword, userType);
                }
            }
            break;

            case R.id.txtDateOfBirth: {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int d, int m, int y) {
                        calendar.set(d, m, y);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        txtDateOfBirth.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);

                datePickerDialog.show();

            }
            break;
        }
    }

    @Override
    public void showLoadingProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideLoadingProgress() {
        loadingDialog.dismiss();
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(StringConstant.PREF_USERNAME, edtUsername.getText());
        startActivity(intent);
    }

    @Override
    public void showLastNameInputError(String message) {
        txtLastNameValidate.setVisibility(View.VISIBLE);
        txtLastNameValidate.setText(message);
    }

    @Override
    public void showFirstNameInputError(String message) {
        txtFirstNameValidate.setVisibility(View.VISIBLE);
        txtFirstNameValidate.setText(message);
    }

    @Override
    public void showUsernameInputError(String message) {
        txtUsernameValidate.setVisibility(View.VISIBLE);
        txtUsernameValidate.setText(message);
    }

    @Override
    public void showPasswordInputError(String message) {
        txtPasswordValidate.setVisibility(View.VISIBLE);
        txtPasswordValidate.setText(message);
    }

    @Override
    public void showConfirmPasswordInputError(String message) {
        txtConfirmPasswordValidate.setVisibility(View.VISIBLE);
        txtConfirmPasswordValidate.setText(message);
    }

    @Override
    public void showPhoneInputError(String message) {
        txtPhoneValidate.setVisibility(View.VISIBLE);
        txtPhoneValidate.setText(message);
    }

    @Override
    public void showAddressInputError(String message) {
        txtAddressValidate.setVisibility(View.VISIBLE);
        txtAddressValidate.setText(message);
    }
}
