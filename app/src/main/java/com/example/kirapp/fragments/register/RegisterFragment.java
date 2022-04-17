package com.example.kirapp.fragments.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.kirapp.R;
import com.example.kirapp.activities.LoginActivity;
import com.example.kirapp.models.Customer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Objects;


public class RegisterFragment extends Fragment {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customers");
    private String createdAt, updatedAt;
    private MaterialButton birthDateBtn;
    private TextInputEditText etFname, etLname, etEmail, etPassword, etPhoneNumber;
    private RadioGroup radioGroup;
    private String gender;
    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        createdAt = LocalDate.now().toString();
        updatedAt = LocalDate.now().toString();
        birthDateBtn = view.findViewById(R.id.user_birthdate);
        datePicker();
        etFname = view.findViewById(R.id.user_firstname);
        etLname = view.findViewById(R.id.user_lastname);
        etEmail = view.findViewById(R.id.user_email);
        etPassword = view.findViewById(R.id.user_password);
        etPhoneNumber = view.findViewById(R.id.user_phone);
        radioGroup = view.findViewById(R.id.user_gender);
        auth = FirebaseAuth.getInstance();
        MaterialButton registerBtn = view.findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(this::register);
        MaterialButton backbtn = view.findViewById(R.id.back_btn);
        backbtn.setOnClickListener(view1 -> startActivity(new Intent(view1.getContext(), LoginActivity.class)));
        return view;
    }

    private void register(View view) {
        if (validateInput(view)) {
            String id = databaseReference.push().getKey();
            Customer customer = new Customer(id, Objects.requireNonNull(etFname.getText()).toString(),
                    Objects.requireNonNull(etLname.getText()).toString(),
                    Objects.requireNonNull(etEmail.getText()).toString(),
                    Objects.requireNonNull(etPassword.getText()).toString(),
                    birthDateBtn.getText().toString(),
                    Objects.requireNonNull(etPhoneNumber.getText()).toString(),
                    gender, true, createdAt, updatedAt);
            auth.createUserWithEmailAndPassword(customer.getEmail(), customer.getPassword())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Objects.requireNonNull(auth.getCurrentUser())
                                    .sendEmailVerification()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            databaseReference.child(Objects.requireNonNull(id)).setValue(customer);
                                        }
                                    });
                            //BURADAN EMAİLİNİ KONTROL ET FRAGMENTİNE GEÇECEK
                            Fragment verificationFragment = new VerificationFragment();
                            FragmentManager fragmentManager = getChildFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.register_layout, verificationFragment).commit();
                        } else {
                            Toast.makeText(view.getContext(), R.string.register_error, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean validateInput(View view) {
        if (Objects.requireNonNull(etFname.getText()).toString().equals("")) {
            Toast.makeText(view.getContext(), R.string.fn_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Objects.requireNonNull(etLname.getText()).toString().equals("")) {
            Toast.makeText(view.getContext(), R.string.ln_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (birthDateBtn.getText().toString().equals("")) {
            Toast.makeText(view.getContext(), R.string.bd_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Objects.requireNonNull(etEmail.getText()).toString().equals("")) {
            Toast.makeText(view.getContext(), R.string.e_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        int MIN_PASSWORD_LENGTH = 8;
        if (Objects.requireNonNull(etPassword.getText()).length() < MIN_PASSWORD_LENGTH) {
            Toast.makeText(view.getContext(), R.string.p_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Objects.requireNonNull(etPhoneNumber.getText()).toString().equals("")) {
            Toast.makeText(view.getContext(), R.string.pn_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = requireView().findViewById(id);
        gender = radioButton.getText().toString();

        return true;
    }

    private void datePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(R.string.select_date);
        final MaterialDatePicker<Long> picker = builder.build();
        birthDateBtn.setOnClickListener(view -> picker.show(requireActivity().getSupportFragmentManager(), "DATE_PICKER"));
        picker.addOnPositiveButtonClickListener(selection -> birthDateBtn.setText(picker.getHeaderText()));
    }
}