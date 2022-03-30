package com.example.kirapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kirapp.R;
import com.example.kirapp.models.Customer;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    List<Customer> customers;

    public CustomerAdapter(List<Customer> customers) {
        this.customers = customers;
    }

    public CustomerAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.customerFirstname.setText(customers.get(position).getFirstname());
        holder.customerLastname.setText(customers.get(position).getFirstname());
        holder.customerPhoto.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView customerFirstname;
        public TextView customerLastname;
        public ImageView customerPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerFirstname = itemView.findViewById(R.id.header);
            customerLastname = itemView.findViewById(R.id.subheader);
            customerPhoto = itemView.findViewById(R.id.image_base);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
