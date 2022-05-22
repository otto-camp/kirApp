package com.example.kirapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kirapp.R;
import com.example.kirapp.adapters.SearchAdvertAdapter;
import com.example.kirapp.models.Advert;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("adverts");
    private final ArrayList<Advert> adverts = new ArrayList<>();
    private final Map<String, Advert> map = new HashMap<>();
    private Advert advert = new Advert();
    private RecyclerView recyclerView;
    private SearchAdvertAdapter advertAdapter;
    private FirebaseRecyclerOptions<Advert> options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.search_advert_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        options = new FirebaseRecyclerOptions.Builder<Advert>()
                .setQuery(FirebaseDatabase.getInstance().getReference("adverts"), Advert.class)
                .build();

        getAdverts(recyclerView);

        return view;
    }





    private void getAdverts(RecyclerView recyclerView) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adverts.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    advertAdapter = new SearchAdvertAdapter(options, adverts, snapshot.getKey(), getContext());
                    for (DataSnapshot s : ds.getChildren()) {
                        advert = s.getValue(Advert.class);
                        map.put(s.getKey(), advert);
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