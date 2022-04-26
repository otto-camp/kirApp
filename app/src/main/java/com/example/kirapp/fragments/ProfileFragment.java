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
    private final DatabaseReference customerReference = FirebaseDatabase.getInstance().getReference();
    private Customer customer = new Customer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView tName = view.findViewById(R.id.user_name);
        TextView tCreatedAt = view.findViewById(R.id.user_created_at);
        TextView tAdvertCount = view.findViewById(R.id.user_advert_count);
        MaterialButton signOut = view.findViewById(R.id.profile_sign_out);

        getProfile(tName, tCreatedAt, tAdvertCount);

        signOut.setOnClickListener(view1 -> {
            auth.signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
        });

        return view;
    }

    private void getProfile(TextView tName, TextView tCreatedAt, TextView tAdvertCount) {
        customerReference.child("customers").child(Objects.requireNonNull(user).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customer = snapshot.getValue(Customer.class);
                String name = Objects.requireNonNull(customer).getFirstname() + " " + customer.getLastname();
                String createdAt = getString(R.string.createdAt) + customer.getCreatedAt();
                tName.setText(name);
                tCreatedAt.setText(createdAt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), R.string.profile_cant_loaded, Toast.LENGTH_LONG).show();
            }
        });
        customerReference.child("adverts").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                String c = getString(R.string.advert_count) + Long.toString(count);
                tAdvertCount.setText(c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
