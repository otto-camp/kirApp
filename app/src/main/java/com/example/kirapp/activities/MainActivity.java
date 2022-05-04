package com.example.kirapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.fragments.AddAdvertFragment;
import com.example.kirapp.fragments.MainPageFragment;
import com.example.kirapp.fragments.MessageFragment;
import com.example.kirapp.fragments.ProfileFragment;
import com.example.kirapp.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;

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
                        fragment = new MainPageFragment();
                    }
                    if (item.getItemId() == R.id.searchFragment) {
                        fragment = new SearchFragment();
                    }
                    if (item.getItemId() == R.id.profileFragment) {
                        fragment = new ProfileFragment();
                    }
                    if (item.getItemId() == R.id.messageFragment) {
                        fragment = new MessageFragment();
                    }

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame_layout, Objects.requireNonNull(fragment)).commit();
                    return true;
                }
        );
        floatingActionButton.setOnClickListener(view -> fragment = new AddAdvertFragment());


    }
}

