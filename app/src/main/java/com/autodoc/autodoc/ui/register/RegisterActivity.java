package com.autodoc.autodoc.ui.register;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.autodoc.autodoc.R;
import com.autodoc.autodoc.api.request.NewUserRequest;
import com.autodoc.autodoc.ui.login.LoginActivity;
import com.autodoc.autodoc.util.AppUtil;
import com.autodoc.autodoc.util.MyTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ilabs on 8/18/18.
 */

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText userNameEditText;

    @BindView(R.id.password)
    EditText passwordEditText;

    @BindView(R.id.name)
    EditText nameNameEditText;

    @BindView(R.id.address)
    EditText addressEditText;

    @BindView(R.id.phone)
    EditText phoneEditText;

    @BindView(R.id.input_layout_username)
    TextInputLayout userNameTextInputLayout;

    @BindView(R.id.input_layout_password)
    TextInputLayout passwordTextInputLayout;

    @BindView(R.id.input_layout_name)
    TextInputLayout nameTextInputLayout;

    @BindView(R.id.input_layout_phone)
    TextInputLayout phoneTextInputLayout;

    @BindView(R.id.input_layout_address)
    TextInputLayout addressTextInputLayout;

    private RegisterViewModel registerViewModel;

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.register));

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        userNameEditText.addTextChangedListener(new MyTextWatcher(userNameTextInputLayout, getString(R.string.invalid_input)));
        passwordEditText.addTextChangedListener(new MyTextWatcher(passwordTextInputLayout, getString(R.string.invalid_input)));
        nameNameEditText.addTextChangedListener(new MyTextWatcher(nameTextInputLayout, getString(R.string.invalid_input)));
        addressEditText.addTextChangedListener(new MyTextWatcher(addressTextInputLayout, getString(R.string.invalid_input)));
        phoneEditText.addTextChangedListener(new MyTextWatcher(phoneTextInputLayout, getString(R.string.invalid_input)));

    }

    @OnClick(R.id.registerButton)
    void registerOnClick() {

        if (!AppUtil.checkNetwork(this)) {
            return;
        }

        String name = nameNameEditText.getText().toString().trim();
        String userName = userNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        nameTextInputLayout.setError("");
        userNameTextInputLayout.setError("");
        passwordTextInputLayout.setError("");
        phoneTextInputLayout.setError("");
        addressTextInputLayout.setError("");

        if (name.isEmpty()) {
            nameTextInputLayout.setError(getString(R.string.invalid_input));
            return;
        }

        if (userName.isEmpty()) {
            userNameTextInputLayout.setError(getString(R.string.invalid_input));
            return;
        }

        if (password.isEmpty()) {
            passwordTextInputLayout.setError(getString(R.string.invalid_input));
            return;
        }

        if (phone.isEmpty()) {
            phoneTextInputLayout.setError(getString(R.string.invalid_input));
            return;
        }

        if (address.isEmpty()) {
            addressTextInputLayout.setError(getString(R.string.invalid_input));
            return;
        }

        NewUserRequest newUserRequest = new NewUserRequest();
        newUserRequest.setName(name);
        newUserRequest.setUserName(userName);
        newUserRequest.setPhone(phone);
        newUserRequest.setPassword(password);
        newUserRequest.setAddress(address);

        registerViewModel.callRegisterRx(this, newUserRequest).observe(this, success -> {
            if (success.booleanValue()) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });


    }


}
