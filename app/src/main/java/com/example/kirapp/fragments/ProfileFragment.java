package com.example.kirapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.activities.LoginActivity;
import com.example.kirapp.models.Customer;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseUser user = auth.getCurrentUser();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReference("customers").child(Objects.requireNonNull(user).getUid());
    String name, createdAt;
    private Customer customer = new Customer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (user != null) {
            getProfile();
        }
    }

    public void getProfile() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customer = snapshot.getValue(Customer.class);
                name = customer.getFirstname() + " " + customer.getLastname();
                createdAt = customer.getCreatedAt();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Profile details can't loaded", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView tName = view.findViewById(R.id.user_name);
        TextView tCreatedAt = view.findViewById(R.id.user_created_at);
        MaterialButton signOut = view.findViewById(R.id.profile_sign_out);

        tName.setText(name);
        tCreatedAt.setText(createdAt);

        signOut.setOnClickListener(view1 -> {
            auth.signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
        });

        return view;
    }

}
