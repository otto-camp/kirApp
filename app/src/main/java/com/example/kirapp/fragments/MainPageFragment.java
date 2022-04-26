package com.example.kirapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kirapp.R;
import com.example.kirapp.adapters.AdvertAdapter;
import com.example.kirapp.models.Advert;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainPageFragment extends Fragment {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("adverts");
    private RecyclerView recyclerView;
    private AdvertAdapter advertAdapter;
    private ArrayList<Advert> adverts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        recyclerView = view.findViewById(R.id.advert_view);

        adverts = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adverts.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Advert advert = dataSnapshot.getValue(Advert.class);
                    adverts.add(advert);
                }
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(manager);
                advertAdapter = new AdvertAdapter(adverts, requireContext());
                recyclerView.setAdapter(advertAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return view;
    }
}