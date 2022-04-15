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

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        MaterialButton forgotPassword = findViewById(R.id.fp_btn);
        MaterialButton back = findViewById(R.id.back_btn);
        TextInputEditText etEmail = findViewById(R.id.fp_email);

        String email = Objects.requireNonNull(etEmail.getText()).toString();

        forgotPassword.setOnClickListener(view -> sendLink(email));
        back.setOnClickListener(view -> startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class)));
    }

    private void sendLink(String email) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), R.string.email_empty, Toast.LENGTH_SHORT).show();
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), R.string.fp_link_send, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.fp_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}