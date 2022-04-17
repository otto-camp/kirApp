package com.example.kirapp.fragments.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.activities.LoginActivity;
import com.google.android.material.button.MaterialButton;

public class VerificationFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verification, container, false);
        MaterialButton back = view.findViewById(R.id.v_back_btn);
        back.setOnClickListener(view1 -> startActivity(new Intent(view1.getContext(), LoginActivity.class)));
        return view;
    }
}