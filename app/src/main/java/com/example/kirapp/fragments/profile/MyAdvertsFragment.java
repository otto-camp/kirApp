package com.example.kirapp.fragments.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.adapters.profile.ProfileAdvertAdapter;
import com.example.kirapp.models.Advert;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdvertsFragment extends Fragment {
    private final ArrayList<Advert> adverts = new ArrayList<>();
    private final ProfileAdvertAdapter advertAdapter = new ProfileAdvertAdapter(adverts, getContext());
    private final Advert advert = new Advert();
    private FirebaseUser user;
    private FirebaseAuth auth;
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("adverts");

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        user = auth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_adverts, container, false);


        return view;
    }
}