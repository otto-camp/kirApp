package com.example.kirapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kirapp.R;
import com.example.kirapp.adapters.AdvertAdapter;
import com.example.kirapp.models.Advert;
import com.example.kirapp.utils.ItemListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainPageFragment extends Fragment {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("adverts");
    private final ArrayList<Advert> adverts = new ArrayList<>();
    private Advert advert = new Advert();
    ItemListener itemListener = (position, id) -> {
        AdvertDetailsFragment advertDetailsFragment = new AdvertDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("AdvertId", advert.getId());
        advertDetailsFragment.setArguments(bundle);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_frame_layout, advertDetailsFragment).addToBackStack(null).commit();
    };
    private final AdvertAdapter advertAdapter = new AdvertAdapter(adverts, getContext(), itemListener);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.advert_view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        getAdverts(recyclerView);

        return view;
    }

    private void getAdverts(RecyclerView recyclerView) {
        databaseReference.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adverts.clear();
                Map<String, Advert> map = new HashMap<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        advert = dataSnapshot1.getValue(Advert.class);
                        map.put(dataSnapshot1.getKey(), advert);
                    }
                }
                adverts.addAll(map.values());
                recyclerView.setAdapter(advertAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}