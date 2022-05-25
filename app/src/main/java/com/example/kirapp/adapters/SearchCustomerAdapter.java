package com.example.kirapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.kirapp.R;
import com.example.kirapp.models.Customer;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchCustomerAdapter extends ArrayAdapter<Customer> implements Filterable {
    private final List<Customer> filteredCustomers;
    private List<Customer> orgCustomers;

    public SearchCustomerAdapter(@NonNull Context context, int resource, @NonNull List<Customer> objects) {
        super(context, resource, objects);
        this.orgCustomers = objects;
        this.filteredCustomers = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = convertView;
        if (item == null) {
            item = LayoutInflater.from(getContext()).inflate(R.layout.search_customer_item, parent, false);
        }
        Customer customer = getItem(position);
        TextView cName = item.findViewById(R.id.search_customer_name);
        TextView cPhone = item.findViewById(R.id.search_customer_phone);
        ShapeableImageView cImage = item.findViewById(R.id.search_customer_image);

        cName.setText(String.format("%s %s", customer.getFirstname(), customer.getLastname()));
        cPhone.setText(customer.getPhoneNumber().substring(7));
        Glide.with(item).load(customer.getImage()).into(cImage);


        return item;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String s = charSequence.toString();

                if (s.isEmpty()) {
                    orgCustomers = filteredCustomers;
                } else {
                    ArrayList<Customer> newList = new ArrayList<>();
                    for (Customer c : filteredCustomers) {
                        if (c.getFirstname().toLowerCase(Locale.ROOT).contains(s)) {
                            newList.add(c);
                        }
                    }
                    orgCustomers = newList;
                }
                FilterResults results = new FilterResults();
                results.values = orgCustomers;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                orgCustomers = (ArrayList<Customer>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
}
