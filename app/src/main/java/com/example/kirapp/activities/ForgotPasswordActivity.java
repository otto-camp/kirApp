package com.example.kirapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kirapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextInputEditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        auth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.fp_email);
        MaterialButton resetPasswordBtn = findViewById(R.id.fp_btn);
        MaterialButton backBtn = findViewById(R.id.back_btn);

        resetPasswordBtn.setOnClickListener(view ->
                sendLink(Objects.requireNonNull(etEmail.getText()).toString().trim()));

        backBtn.setOnClickListener(view ->
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class)));
    }

    private void sendLink(String email) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, R.string.email_empty, Toast.LENGTH_SHORT).show();
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, R.string.fp_link_send, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.fp_error, Toast.LENGTH_LONG).show();
            }
        });
    }


}