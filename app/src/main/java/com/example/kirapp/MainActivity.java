package com.example.kirapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kirapp.fragments.MainPageFragment;
import com.example.kirapp.fragments.ProfileFragment;
import com.example.kirapp.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MainPageFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.mainPageFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.mainPageFragment:
                    fragment = new MainPageFragment();
                    break;
                case R.id.profileFragment:
                    fragment = new ProfileFragment();
                    break;
                case R.id.searchFragment:
                    fragment = new SearchFragment();
                    break;
            }
            assert fragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,fragment).commit();
            return true;
        });
    }
}