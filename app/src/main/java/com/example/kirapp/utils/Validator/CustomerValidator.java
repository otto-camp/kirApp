package com.example.kirapp.utils.Validator;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.kirapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class CustomerValidator implements TextInputValidator {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" + "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{8,}" + "$");

    @Override
    public boolean confirmInput() {
        return false;
    }

    @Override
    public String validateEmail(TextInputEditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(String.valueOf(R.string.input_empty));
            return String.valueOf(R.string.input_empty);
        }
        String input = editText.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            editText.setError(String.valueOf(R.string.email_isnot_valid));
        }
        return input;
    }

    @Override
    public String validatePassword(TextInputEditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(String.valueOf(R.string.input_empty));
            return String.valueOf(R.string.input_empty);
        } else if (!PASSWORD_PATTERN.matcher(editText.getText().toString()).matches()) {
            editText.setError(String.valueOf(R.string.password_isnot_valid));
        }
        return editText.getText().toString();
    }

    @Override
    public String validateNumber(TextInputEditText editText) {
        return null;
    }

    @Override
    public String validateInput(TextInputEditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(String.valueOf(R.string.input_empty));
        }
        return editText.getText().toString();

    }


}
