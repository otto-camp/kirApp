package com.example.kirapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.fragments.passwordreset.SendLinkFragment;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Fragment fragment = new SendLinkFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.send_link_layout, fragment).commit();
    }
}