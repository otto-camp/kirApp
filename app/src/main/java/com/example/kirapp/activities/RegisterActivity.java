package com.example.kirapp.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kirapp.R;
import com.example.kirapp.models.Customer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private final Calendar calendar = Calendar.getInstance();
    private EditText etBirthDate, etFname, etLname, etEmail, etPassword, etPhoneNumber;
    private RadioGroup etGender;
    private RadioButton radioButton;
    private Customer customer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        datePicker();
        viewInit();
    }

    public void register(View view){
        if (validateInput()){
            customer.setFirstname(etFname.getText().toString());
            customer.setLastname(etLname.getText().toString());
            customer.setBirthDate(etBirthDate.getText().toString());
            customer.setEmail(etEmail.getText().toString());
            customer.setPassword(etPassword.getText().toString());
            customer.setPhoneNumber(etPhoneNumber.getText().toString());
        }
    }

    private void viewInit() {
        etFname = findViewById(R.id.user_firstname);
        etLname = findViewById(R.id.user_lastname);
        etBirthDate = findViewById(R.id.user_birthdate);
        etEmail = findViewById(R.id.user_email);
        etPassword = findViewById(R.id.user_password);
        etPhoneNumber = findViewById(R.id.user_phone);
        etGender = findViewById(R.id.user_gender);
    }

    private boolean validateInput() {
        if (etFname.getText().toString().equals("")) {
            etFname.setError("Please Enter Name");
            return false;
        }
        if (etLname.getText().toString().equals("")) {
            etLname.setError("Please Enter Last Name");
            return false;
        }
        if (etBirthDate.getText().toString().equals("")) {
            etBirthDate.setError("Please Enter Birth Date");
            return false;
        }
        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Please Enter Email");
            return false;
        }
        int MIN_PASSWORD_LENGTH = 8;
        if (etPassword.getText().length() < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Password Length Must Be More Then " + MIN_PASSWORD_LENGTH);
            return false;
        }
        if (etPhoneNumber.getText().toString().equals("")) {
            etPhoneNumber.setError("Please Enter Phone Number");
            return false;
        }
        etGender.setOnCheckedChangeListener((radioGroup, i) -> {
            int child = radioGroup.getChildCount();
            for (int x = 0; x < child; x++) {
                radioButton = (RadioButton) radioGroup.getChildAt(x);
                if (radioButton.getId() == R.id.radio_male) {
                    radioButton.setText(R.string.male);
                } else if (radioButton.getId() == R.id.radio_female) {
                    radioButton.setText(R.string.female);
                } else {
                    radioButton.setText(R.string.other);
                }
                if (radioButton.getId() == i) {
                    customer.setGender(radioButton.getText().toString());
                }
            }
        });

        return true;
    }

    private void datePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        };
        etBirthDate.setOnClickListener(view -> new DatePickerDialog(
                RegisterActivity.this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        ).show());
    }

    private void updateLabel() {
        String format = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        etBirthDate.setText(dateFormat.format(calendar.getTime()));
    }

}
