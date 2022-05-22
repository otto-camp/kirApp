package com.example.kirapp.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kirapp.R;
import com.example.kirapp.adapters.profile.ProfileAdvertAdapter;
import com.example.kirapp.models.Advert;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdvertsFragment extends Fragment {
    private final ArrayList<Advert> adverts = new ArrayList<>();
    private final ProfileAdvertAdapter advertAdapter = new ProfileAdvertAdapter(adverts, getContext());
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("adverts");
    private Advert advert = new Advert();
    private FirebaseUser user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_adverts, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.user_my_adverts_recyler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        getAdverts(recyclerView);

        return view;
    }

    private void getAdverts(RecyclerView recyclerView) {
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adverts.clear();
                Map<String, Advert> map = new HashMap<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    advert = s.getValue(Advert.class);
                    map.put(s.getKey(), advert);
                }
                adverts.addAll(map.values());
                recyclerView.setAdapter(advertAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error accured", Toast.LENGTH_SHORT).show();
            }
        });
    }
}