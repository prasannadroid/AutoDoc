package com.autodoc.autodoc.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.autodoc.App;
import com.autodoc.autodoc.R;
import com.autodoc.autodoc.ui.home.HomeActivity;
import com.autodoc.autodoc.ui.profile.ProfileActivity;
import com.autodoc.autodoc.ui.register.RegisterActivity;
import com.autodoc.autodoc.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ilabs on 8/17/18.
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText userNameEditText;

    @BindView(R.id.password)
    EditText passwordEditText;

    @BindView(R.id.input_layout_username)
    TextInputLayout userNameTextInputLayout;

    @BindView(R.id.input_layout_password)
    TextInputLayout passwordTextInputLayout;

    private LoginViewModel loginViewModel;

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (App.getInstance().getUserSession().getAuthToken() != null) {
            startActivity(new Intent(this, HomeActivity.class));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.login));

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @OnClick(R.id.loginButton)
    void loginClick() {

        if (!AppUtil.checkNetwork(this)) {
            return;
        }

        String userName = userNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (validateUserName(userName) && validatePassword(password)) {
            loginViewModel.callLoginRx(LoginActivity.this, userName, password).observe(LoginActivity.this, success -> {
                if (success.booleanValue()) {
                    startActivity(new Intent(this, HomeActivity.class));
                }
            });
        }

    }

    @OnClick(R.id.registerButtonButton)
    void navigateRegister() {
        startActivity(new Intent(this, RegisterActivity.class));

    }

    private boolean validateUserName(String email) {
        if (email.isEmpty()) {
            userNameTextInputLayout.setError(getString(R.string.err_msg_email));
            return false;
        } else {
            userNameTextInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
            passwordTextInputLayout.setError(getString(R.string.err_msg_pass));
            return false;
        } else {
            passwordTextInputLayout.setErrorEnabled(false);
        }
        return true;
    }
}
