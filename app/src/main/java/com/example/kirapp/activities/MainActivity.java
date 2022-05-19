package com.example.kirapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kirapp.R;
import com.example.kirapp.fragments.AddAdvertFragment;
import com.example.kirapp.fragments.MainPageFragment;
import com.example.kirapp.fragments.MessageFragment;
import com.example.kirapp.fragments.ProfileFragment;
import com.example.kirapp.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FloatingActionButton floatingActionButton = findViewById(R.id.add_advert_btn);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, new MainPageFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.mainPageFragment);
        bottomNavigationView.setOnItemSelectedListener(item -> {
                    if (item.getItemId() == R.id.mainPageFragment) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frame_layout, new MainPageFragment()).commit();
                    }
                    if (item.getItemId() == R.id.searchFragment) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frame_layout, new SearchFragment()).commit();
                    }
                    if (item.getItemId() == R.id.profileFragment) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frame_layout, new ProfileFragment()).commit();
                    }
                    if (item.getItemId() == R.id.messageFragment) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frame_layout, new MessageFragment()).commit();
                    }
                    return true;
                }
        );
        floatingActionButton.setOnClickListener(view -> getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, new AddAdvertFragment())
                .commit());
    }
}

