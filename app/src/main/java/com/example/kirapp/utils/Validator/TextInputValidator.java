package com.example.kirapp.utils.Validator;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public interface TextInputValidator {
    boolean confirmInput();
    String validateInput(TextInputEditText editText);
    String validateEmail(TextInputEditText editText);
    String validatePassword(TextInputEditText editText);
    String validateNumber(TextInputEditText editText);
}
