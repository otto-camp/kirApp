package com.example.kirapp.fragments.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;


public class ListAllAdvertsFragment extends Fragment {
    private ArrayList<Advert> adverts = new ArrayList<>();
    private AdvertAdapter advertAdapter = new AdvertAdapter(adverts,getContext());
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("adverts");
    private Advert advert = new Advert();
    private Map<String, Advert> map = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_all_adverts, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list_all_adverts_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        getAdverts(recyclerView);
        return view;
    }

    private void getAdverts(RecyclerView recyclerView) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adverts.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    advertAdapter = new AdvertAdapter(adverts, getContext(),dataSnapshot.getKey());
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
                Toast.makeText(getContext(), "Data couldn't fetched", Toast.LENGTH_SHORT).show();
            }
        });
    }
}