package com.autodoc.autodoc.util;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by ilabs on 8/23/18.
 */

public class MyTextWatcher implements TextWatcher {
    private TextInputLayout textInputLayout;
    private String message;

    public MyTextWatcher(TextInputLayout textInputLayout, String message) {
        this.textInputLayout = textInputLayout;
        this.message = message;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence != null && charSequence.toString().length() > 0) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setError(message);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
