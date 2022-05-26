package com.example.kirapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kirapp.R;
import com.example.kirapp.fragments.AddAdvertFragment;
import com.example.kirapp.fragments.MainPageFragment;
import com.example.kirapp.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user!=null &&user.getUid().equals("eV7ZvoMfJMdC7LWV1iDZuexoQU42")) {
            startActivity(new Intent(this, AdminDashboardActivity.class));
        } else {

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

                        if (item.getItemId() == R.id.profileFragment) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_frame_layout, new ProfileFragment()).commit();
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

}

