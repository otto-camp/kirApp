package com.example.kirapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kirapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button login, loginGoogle, forgotPassword, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        login = findViewById(R.id.login_btn);
        loginGoogle = findViewById(R.id.login_google_btn);
        forgotPassword = findViewById(R.id.forgot_password_btn);
        register = findViewById(R.id.register_btn);
    }


}