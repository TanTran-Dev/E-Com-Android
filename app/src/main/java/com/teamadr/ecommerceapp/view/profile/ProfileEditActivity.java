package com.teamadr.ecommerceapp.view.profile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.constants.Gender;
import com.teamadr.ecommerceapp.constants.StringConstant;
import com.teamadr.ecommerceapp.constants.UserType;
import com.teamadr.ecommerceapp.custom_view.LoadingDialog;
import com.teamadr.ecommerceapp.model.request.salesman.NewSalesmanDto;
import com.teamadr.ecommerceapp.model.request.customer.NewCustomerDto;
import com.teamadr.ecommerceapp.model.response.salesman.SalesmanDto;
import com.teamadr.ecommerceapp.model.response.customer.CustomerDto;
import com.teamadr.ecommerceapp.presenter.profile_edit.ProfileEditPresenter;
import com.teamadr.ecommerceapp.presenter.profile_edit.ProfileEditPresenterImpl;
import com.teamadr.ecommerceapp.utils.UserAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileEditActivity extends AppCompatActivity implements ProfileEditView, View.OnClickListener {
    @BindView(R.id.edtFirstName)
    TextInputEditText edtFirstName;
    @BindView(R.id.edtLastName)
    TextInputEditText edtLastName;
    @BindView(R.id.edtAddress)
    TextInputEditText edtAddress;
    @BindView(R.id.edtPhoneNumber)
    TextInputEditText edtPhoneNumber;
    @BindView(R.id.txtDateOfBirth)
    TextView txtDateOfBirth;
    @BindView(R.id.radioMale)
    RadioButton radioMale;
    @BindView(R.id.radioFemale)
    RadioButton radioFemale;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tool_bar_title)
    TextView toolbarTitle;
    @BindView(R.id.btnUpdateProfile)
    Button btnUpdateProfile;

    private SalesmanDto salesmanDto;
    private CustomerDto customerDto;

    private ProfileEditPresenter profileEditPresenter;
    protected LoadingDialog loadingDialog;
    private String avatarUrl;
    private String coverUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            toolbarTitle.setText("Chỉnh sửa thông tin cá nhân");
        }

        initPresenter();
        initView();
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

    private void initPresenter() {
        profileEditPresenter = new ProfileEditPresenterImpl(this, this);
    }

    private void initView() {
        if (UserType.SALESMAN.getLabel().equals(UserAuth.getUserType(this))) {
            salesmanDto = (SalesmanDto) getIntent().getSerializableExtra(StringConstant.KEY_USER_PROFILE);
            avatarUrl = salesmanDto.getAvatarUrl();
            coverUrl = salesmanDto.getImageCoverUrl();
            edtFirstName.setText(salesmanDto.getFirstName());
            edtLastName.setText(salesmanDto.getLastName());
            edtAddress.setText(salesmanDto.getAddress());
            txtDateOfBirth.setText(salesmanDto.getBirthDay());
            edtPhoneNumber.setText(salesmanDto.getPhone());
            if (salesmanDto.getGender().getValue().equals(Gender.MALE.getValue())) {
                radioMale.setChecked(true);
            } else {
                radioFemale.setChecked(true);
            }
        } else {
            customerDto = (CustomerDto) getIntent().getSerializableExtra(StringConstant.KEY_USER_PROFILE);
            avatarUrl = customerDto.getAvatarUrl();
            coverUrl = customerDto.getImageCoverUrl();
            edtFirstName.setText(customerDto.getFirstName());
            edtLastName.setText(customerDto.getLastName());
            edtAddress.setText(customerDto.getAddress());
            edtPhoneNumber.setText(customerDto.getPhone());
            txtDateOfBirth.setText(customerDto.getBirthDay());
            if (customerDto.getGender().getValue().equals(Gender.MALE.getValue())) {
                radioMale.setChecked(true);
            } else {
                radioFemale.setChecked(true);
            }
        }

        txtDateOfBirth.setOnClickListener(this);
        btnUpdateProfile.setOnClickListener(this);
        loadingDialog = new LoadingDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

            case R.id.btnUpdateProfile: {
                if (UserType.SALESMAN.getLabel().equals(UserAuth.getUserType(this))) {
                    NewSalesmanDto newAdminProfileDto = new NewSalesmanDto();

                    newAdminProfileDto.setUserName(UserAuth.getUsername(this));
                    newAdminProfileDto.setPassword(UserAuth.getUserPassword(this));
                    newAdminProfileDto.setFirstName(edtFirstName.getText().toString());
                    newAdminProfileDto.setLastName(edtLastName.getText().toString());
                    newAdminProfileDto.setAddress(edtAddress.getText().toString());
                    newAdminProfileDto.setBirthDay(txtDateOfBirth.getText().toString());
                    newAdminProfileDto.setAvatarUrl(avatarUrl);
                    newAdminProfileDto.setImageCoverUrl(coverUrl);
                    newAdminProfileDto.setUserType(UserType.SALESMAN);
                    if (radioMale.isChecked()) {
                        newAdminProfileDto.setGender(Gender.MALE);
                    }

                    if (radioFemale.isChecked()) {
                        newAdminProfileDto.setGender(Gender.FEMALE);
                    }

                    newAdminProfileDto.setPhone(edtPhoneNumber.getText().toString());

                    profileEditPresenter.editProfileAdmin(newAdminProfileDto);
                } else {

                    NewCustomerDto newCustomerProfileDto = new NewCustomerDto();

                    newCustomerProfileDto.setUserName(UserAuth.getUsername(this));
                    newCustomerProfileDto.setPassword(UserAuth.getUserPassword(this));
                    newCustomerProfileDto.setFirstName(edtFirstName.getText().toString());
                    newCustomerProfileDto.setLastName(edtLastName.getText().toString());
                    newCustomerProfileDto.setAddress(edtAddress.getText().toString());
                    newCustomerProfileDto.setBirthDay(txtDateOfBirth.getText().toString());
                    newCustomerProfileDto.setAvatarUrl(avatarUrl);
                    newCustomerProfileDto.setImageCoverUrl(coverUrl);
                    newCustomerProfileDto.setUserType(UserType.CUSTOMER);

                    if (radioMale.isChecked()) {
                        newCustomerProfileDto.setGender(Gender.MALE);
                    }

                    if (radioFemale.isChecked()) {
                        newCustomerProfileDto.setGender(Gender.FEMALE);
                    }

                    newCustomerProfileDto.setPhone(edtPhoneNumber.getText().toString());

                    profileEditPresenter.editProfileCustomer(newCustomerProfileDto);
                }
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
    public void navigateToProfile() {
        finish();
    }
}
