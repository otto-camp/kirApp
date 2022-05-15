package com.example.kirapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kirapp.R;
import com.example.kirapp.models.Customer;
import com.example.kirapp.utils.Validator.TextInputValidator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.time.LocalDate;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customers");
    private final Customer customer = new Customer();
    private String createdAt;
    private String updatedAt;
    private MaterialButton birthDateBtn;
    private TextInputEditText etFname, etLname, etEmail, etPassword, etPhoneNumber;
    private RadioGroup radioGroup;
    private String gender;
    private CountryCodePicker cpp;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextInputValidator validator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        auth = FirebaseAuth.getInstance();

        createdAt = LocalDate.now().toString();
        updatedAt = LocalDate.now().toString();

        birthDateBtn = findViewById(R.id.user_birthdate);
        datePicker();

        etFname = findViewById(R.id.user_firstname);
        etLname = findViewById(R.id.user_lastname);
        etEmail = findViewById(R.id.user_email);
        etPassword = findViewById(R.id.user_password);
        etPhoneNumber = findViewById(R.id.user_phone);
        cpp = findViewById(R.id.ccp);
        radioGroup = findViewById(R.id.user_gender);

        MaterialButton registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(view -> create(customer));

        MaterialButton backbtn = findViewById(R.id.back_btn);
        backbtn.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

    }

    public void create(final Customer customer) {
        if (validateInput()) {
            String email = Objects.requireNonNull(etEmail.getText()).toString();
            String password = Objects.requireNonNull(etPassword.getText()).toString();
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            customer.setFirstname(Objects.requireNonNull(etFname.getText()).toString());
                            customer.setLastname(Objects.requireNonNull(etLname.getText()).toString());
                            customer.setEmail(Objects.requireNonNull(etEmail.getText()).toString());
                            customer.setBirthDate(birthDateBtn.getText().toString());
                            customer.setPhoneNumber(cpp.getTextView_selectedCountry().getText().toString() + Objects.requireNonNull(etPhoneNumber.getText()));
                            customer.setGender(gender);
                            customer.setStatus(true);
                            customer.setCreatedAt(createdAt);
                            customer.setUpdatedAt(updatedAt);
                            user = auth.getCurrentUser();
                            Objects.requireNonNull(user).sendEmailVerification();
                            customer.setId(user.getUid());
                            databaseReference.child(customer.getId()).setValue(customer);
                            Toast.makeText(this, R.string.verification_text, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, R.string.register_error, Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    public boolean validateInput() {
        if (Objects.requireNonNull(etFname.getText()).toString().equals("")) {
            Toast.makeText(this, R.string.fn_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Objects.requireNonNull(etLname.getText()).toString().equals("")) {
            Toast.makeText(this, R.string.ln_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (birthDateBtn.getText().toString().equals("")) {
            Toast.makeText(this, R.string.bd_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Objects.requireNonNull(etEmail.getText()).toString().equals("")) {
            Toast.makeText(this, R.string.e_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        int MIN_PASSWORD_LENGTH = 8;
        if (Objects.requireNonNull(etPassword.getText()).length() < MIN_PASSWORD_LENGTH) {
            Toast.makeText(this, R.string.p_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Objects.requireNonNull(etPhoneNumber.getText()).toString().equals("")) {
            Toast.makeText(this, R.string.pn_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(id);
        gender = radioButton.getText().toString();

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
