package com.example.kirapp.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.fragments.register.RegisterFragment;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Fragment fragment = new RegisterFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.register_layout, fragment).commit();
    }


}
