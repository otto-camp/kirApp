package com.example.kirapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kirapp.databinding.FragmentAdvertDetailsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdvertDetailsFragment extends Fragment {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("adverts");
    private FragmentAdvertDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdvertDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String id = getArguments().getString("AdvertId");
        binding.advertPostName.setText(id);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void onBackPressed() {

    }

}