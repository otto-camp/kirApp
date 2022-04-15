package com.example.kirapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kirapp.R;
import com.example.kirapp.models.Customer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customers");
    LocalDate localDate = LocalDate.now();
    private final String createdAt = localDate.toString();
    private final String updatedAt = localDate.toString();
    private MaterialButton birthDateBtn;
    private TextInputEditText etFname, etLname, etEmail, etPassword, etPhoneNumber;
    private RadioButton m,f,o;
    private String gender;
    private FirebaseAuth auth;

    public RegisterActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        Objects.requireNonNull(getSupportActionBar()).hide();

        birthDateBtn = findViewById(R.id.user_birthdate);
        datePicker();
        etFname = findViewById(R.id.user_firstname);
        etLname = findViewById(R.id.user_lastname);
        etEmail = findViewById(R.id.user_email);
        etPassword = findViewById(R.id.user_password);
        etPhoneNumber = findViewById(R.id.user_phone);
        m = findViewById(R.id.radio_male);
        f = findViewById(R.id.radio_female);
        o = findViewById(R.id.radio_other);
        auth = FirebaseAuth.getInstance();
        Button button = findViewById(R.id.register_btn);
        button.setOnClickListener(this::register);

    }

    public void register(View view) {
        if (validateInput()) {
            String id = databaseReference.push().getKey();
            Customer customer = new Customer(id, etFname.getText().toString(), etLname.getText().toString(),
                    etEmail.getText().toString(), etPassword.getText().toString(), birthDateBtn.getText().toString(),
                    etPhoneNumber.getText().toString(), gender, true, createdAt, updatedAt);
            auth.createUserWithEmailAndPassword(customer.getEmail(), customer.getPassword()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });
            databaseReference.child(Objects.requireNonNull(id)).setValue(customer);
        }
    }

    private boolean validateInput() {
        if (etFname.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, R.string.fn_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etLname.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, R.string.ln_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (birthDateBtn.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, R.string.bd_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etEmail.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, R.string.e_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        int MIN_PASSWORD_LENGTH = 8;
        if (etPassword.getText().length() < MIN_PASSWORD_LENGTH) {
            Toast.makeText(RegisterActivity.this, R.string.p_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etPhoneNumber.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, R.string.pn_empty, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void datePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(R.string.select_date);
        final MaterialDatePicker<Long> picker = builder.build();

        birthDateBtn.setOnClickListener(view -> picker.show(getSupportFragmentManager(), "DATE_PICKER"));

        picker.addOnPositiveButtonClickListener(selection -> birthDateBtn.setText(picker.getHeaderText()));
    }

}
