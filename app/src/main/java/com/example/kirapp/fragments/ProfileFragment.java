package com.example.kirapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.activities.LoginActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    private String name, email, uid;
    private TextView tName, tEmail;
    private MaterialButton signOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        getProfile(Objects.requireNonNull(user));
    }

    private void getProfile(FirebaseUser user) {
        for (UserInfo profile : user.getProviderData()) {
            uid = profile.getUid();
            name = profile.getDisplayName();
            email = profile.getEmail();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tName = view.findViewById(R.id.user_name);
        tEmail = view.findViewById(R.id.myUser);
        signOut = view.findViewById(R.id.profile_sign_out);

        tName.setText(name);
        tEmail.setText(email);
        signOut.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
        });

        return view;
    }
}