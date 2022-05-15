package com.example.kirapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kirapp.R;
import com.example.kirapp.models.Customer;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    private final ArrayList<Customer> customers;
    private Context context;

    public CustomerAdapter(ArrayList<Customer> customers, Context context) {
        this.customers = customers;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = customers.get(position).getFirstname() + customers.get(position).getLastname();
        holder.tName.setText(name);
        holder.tEmail.setText(customers.get(position).getEmail());
        holder.tBirthdate.setText(customers.get(position).getBirthDate());
        holder.tPhone.setText(customers.get(position).getPhoneNumber());
        holder.tGender.setText(customers.get(position).getGender());
        holder.cImage.setImageResource(R.mipmap.ic_blue_1_foreground);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView cImage;
        private final TextView tName, tEmail, tBirthdate, tPhone, tGender;

        public ViewHolder(View v) {
            super(v);
            cImage = v.findViewById(R.id.admin_customer_image);
            tName = v.findViewById(R.id.admin_customer_name);
            tEmail = v.findViewById(R.id.admin_customer_email);
            tBirthdate = v.findViewById(R.id.admin_customer_birthdate);
            tPhone = v.findViewById(R.id.admin_customer_phone);
            tGender = v.findViewById(R.id.admin_customer_gender);
        }

    }
}
