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
import com.example.kirapp.adapters.CustomerAdapter;
import com.example.kirapp.models.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListAllUsersFragment extends Fragment {
    private final ArrayList<Customer> customers = new ArrayList<>();
    private final CustomerAdapter customerAdapter = new CustomerAdapter(customers, getContext());
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("customers");
    private Customer customer = new Customer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_all_users, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.list_all_users_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        getUsers(recyclerView);

        return view;
    }

    private void getUsers(RecyclerView recyclerView) {
        reference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Customer> map = new HashMap<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    customer = s.getValue(Customer.class);
                    map.put(s.getKey(), customer);
                }
                customers.addAll(map.values());
                recyclerView.setAdapter(customerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error accured", Toast.LENGTH_SHORT).show();
            }
        });
    }
}