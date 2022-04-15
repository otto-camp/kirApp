package com.example.kirapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.fragments.MainPageFragment;
import com.example.kirapp.fragments.ProfileFragment;
import com.example.kirapp.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private final FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MainPageFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.mainPageFragment);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            if (item.getItemId() == R.id.mainPageFragment) {
                fragment = new MainPageFragment();
            }
            if (item.getItemId() == R.id.profileFragment) {
                if (auth.isAnonymous()) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                fragment = new ProfileFragment();
            }
            if (item.getItemId() == R.id.searchFragment) {
                fragment = new SearchFragment();
            }
            assert fragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();
            return true;
        });
    }

}

