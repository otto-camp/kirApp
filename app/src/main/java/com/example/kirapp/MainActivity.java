package com.example.kirapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.kirapp.databinding.ActivityMainBinding;
import com.example.kirapp.fragments.MainPageFragment;
import com.example.kirapp.fragments.ProfileFragment;
import com.example.kirapp.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        replaceFragment(new MainPageFragment());

        mainBinding.bottomNavigationView.setOnClickListener(item -> {
            switch (item.getId()) {
                case R.id.main_page:
                    replaceFragment(new MainPageFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.search:
                    replaceFragment(new SearchFragment());
                    break;
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}