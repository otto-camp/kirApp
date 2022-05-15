package com.example.kirapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kirapp.R;
import com.example.kirapp.fragments.admin.ListAllUsersFragment;
import com.example.kirapp.models.Customer;
import com.google.android.material.button.MaterialButton;

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
        Bundle bundle = new Bundle();

        String name = customers.get(position).getFirstname() + customers.get(position).getLastname();
        holder.tName.setText(name);

        holder.imageResetBtn.setOnClickListener(v -> {
            Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_user);
            bundle.putParcelable("adminCustomerImage", image);
            holder.cImage.setImageResource(R.mipmap.ic_user);
        });
        holder.changeStatusBtn.setOnClickListener(v2 -> {
            String status = String.valueOf(customers.get(position).isStatus());
            holder.changeStatusBtn.setText(status);
            bundle.putString("adminCustomerStatus", status);
        });


        bundle.putString("adminCustomerId", customers.get(position).getId());
        ListAllUsersFragment fragment = new ListAllUsersFragment();
        fragment.setArguments(bundle);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView cImage;
        private final MaterialButton imageResetBtn;
        private final MaterialButton changeStatusBtn;
        private final TextView tName;

        public ViewHolder(View v) {
            super(v);
            cImage = v.findViewById(R.id.admin_customer_image);
            imageResetBtn = v.findViewById(R.id.admin_reset_photo);
            changeStatusBtn = v.findViewById(R.id.admin_status);
            tName = v.findViewById(R.id.admin_customer_name);
        }

    }
}
