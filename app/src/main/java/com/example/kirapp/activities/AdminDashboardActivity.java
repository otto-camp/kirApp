package com.example.kirapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kirapp.R;
import com.example.kirapp.fragments.admin.ListAllAdvertsFragment;
import com.example.kirapp.fragments.admin.ListAllUsersFragment;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminDashboardActivity extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseAuth auth;
    private MaterialButton listAdvertsBtn, listUsersBtn, quitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        listAdvertsBtn = findViewById(R.id.list_all_adverts_btn);
        listUsersBtn = findViewById(R.id.list_all_users_btn);
        quitBtn = findViewById(R.id.admin_quit_btn);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        listUsersBtn.setOnClickListener(v -> getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_frame_layout, new ListAllUsersFragment()).commit());
        listAdvertsBtn.setOnClickListener(v -> getSupportFragmentManager().beginTransaction()
                .replace(R.id.admin_frame_layout, new ListAllAdvertsFragment()).commit());
        quitBtn.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}