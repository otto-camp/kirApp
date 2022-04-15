package com.example.kirapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.fragments.MainPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private FirebaseAuth auth;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        Button login = findViewById(R.id.login_btn);
        Button loginGoogle = findViewById(R.id.login_google_btn);
        Button forgotPassword = findViewById(R.id.forgot_password_btn);
        Button register = findViewById(R.id.register_btn);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(this::signIn);
        register.setOnClickListener(this::register);
        forgotPassword.setOnClickListener(this::forgotPassword);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            reload();
        }
    }

    private void signIn(View view) {
        auth.signInWithEmailAndPassword(
                email.getText().toString().trim(),
                password.getText().toString().trim()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                updateUI(Objects.requireNonNull(user));
            } else {
                Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        });
    }

    private void reload() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            bottomNavigationView.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.profileFragment) {
                    Fragment fragment = new MainPageFragment();
                }
                return true;
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void register(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private void forgotPassword(View view) {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }
}
