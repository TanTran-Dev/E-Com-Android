package com.teamadr.ecommerceapp.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.teamadr.ecommerceapp.event_bus.RegisterEvent;
import com.teamadr.ecommerceapp.utils.UserAuth;
import com.teamadr.ecommerceapp.view.home.HomeActivity;
import com.teamadr.ecommerceapp.R;
import com.teamadr.ecommerceapp.constants.StringConstant;
import com.teamadr.ecommerceapp.custom_view.LoadingDialog;
import com.teamadr.ecommerceapp.presenter.auth.AuthUsernamePasswordPresenter;
import com.teamadr.ecommerceapp.presenter.auth.AuthUsernamePasswordPresenterImpl;
import com.teamadr.ecommerceapp.view.registration.RegistrationActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements AuthenticationView, View.OnClickListener {
    private static final String PREFS_NAME = "preferences";
    private static final String PREF_USERNAME = "Username";
    private static final String PREF_PASSWORD = "Password";

    private final String defaultUsernameValue = "";
    private String usernameValue;

    private final String defaultPasswordValue = "";
    private String passwordValue;

    private boolean isSaveCheckBoxState;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @BindView(R.id.edtUsername)
    TextInputEditText edtUserName;
    @BindView(R.id.txtValidateEmail)
    TextView txtValidateEmail;
    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.chkRemember)
    CheckBox chkRemember;
    @BindView(R.id.btnForget)
    TextView btnForgetPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtRegister)
    TextView txtRegister;

    private AuthUsernamePasswordPresenter presenter;

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initPresenter();
        checkLoggedIn();
        loadPreferences();
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    private void initPresenter() {
        presenter = new AuthUsernamePasswordPresenterImpl(this, this);
    }

    private void initData() {
        edtUserName.setText(getIntent().getStringExtra(StringConstant.PREF_USERNAME));

    }

    private void initView() {
        loadingDialog = new LoadingDialog(this);
        btnLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);

        chkRemember.setChecked(true);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterEvent(RegisterEvent registerEvent) {
        edtUserName.setText(registerEvent.getUsername());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin: {
                if (edtUserName.getText().toString().trim().matches(emailPattern)) {
                    txtValidateEmail.setVisibility(View.GONE);
                } else {
                    txtValidateEmail.setVisibility(View.VISIBLE);
                    txtValidateEmail.setText("Sai định dạng gmail!!");
                }

                presenter.handleLogin(
                        edtUserName.getText().toString().trim(), edtPassword.getText().toString().trim());
                savePreferences();
                UserAuth.saveUserAuth(this, edtUserName.getText().toString().trim(),
                        edtPassword.getText().toString().trim());
            }
            break;
            case R.id.txtRegister: {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.chkRemember: {
                savePreferences();
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
    public void navigateHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        SharedPreferences saveCheckBoxPref = getSharedPreferences(
                StringConstant.KEY_CHECK_BOX_STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = saveCheckBoxPref.edit();

        editor1.putBoolean(StringConstant.KEY_CHECK_BOX_STATE, chkRemember.isChecked());
        isSaveCheckBoxState = chkRemember.isChecked();

        // Edit and commit
        usernameValue = String.valueOf(edtUserName.getText());
        passwordValue = String.valueOf(edtPassword.getText());

        editor.putString(PREF_USERNAME, usernameValue);
        editor.putString(PREF_PASSWORD, passwordValue);

        editor.apply();
        editor1.apply();
    }

    private void loadPreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences saveCheckBoxPref = getSharedPreferences(
                StringConstant.KEY_CHECK_BOX_STATE, Context.MODE_PRIVATE);

        // Get value
        usernameValue = settings.getString(PREF_USERNAME, defaultUsernameValue);
        passwordValue = settings.getString(PREF_PASSWORD, defaultPasswordValue);
        isSaveCheckBoxState = saveCheckBoxPref.getBoolean(StringConstant.KEY_CHECK_BOX_STATE, false);

        if (isSaveCheckBoxState) {
            chkRemember.setChecked(true);
            edtUserName.setText(usernameValue);
            edtPassword.setText(passwordValue);
        } else {
            chkRemember.setChecked(false);
            edtUserName.setText(defaultUsernameValue);
            edtPassword.setText(defaultPasswordValue);
        }
    }

    private void checkLoggedIn() {
        SharedPreferences preferences = getSharedPreferences(StringConstant.SHARE_PREF_AUTH,
                Context.MODE_PRIVATE);
        if (preferences.getBoolean(StringConstant.KEY_LOGGED_IN, false)) {
            navigateHome();
            return;
        }
    }
}
