package com.example.kirapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kirapp.R;
import com.example.kirapp.models.Customer;

// FIXME: 21.04.2022
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    Customer customer;
    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tUsername, tUserAdvertCount, tEmail;

        public ViewHolder(View view) {
            super(view);
            tUsername = view.findViewById(R.id.user_name);
            tUserAdvertCount = view.findViewById(R.id.user_advert_count);
            tEmail = view.findViewById(R.id.user_created_at);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
