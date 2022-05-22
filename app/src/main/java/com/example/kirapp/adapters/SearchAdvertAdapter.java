package com.example.kirapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kirapp.R;
import com.example.kirapp.models.Advert;
import com.example.kirapp.models.Customer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SearchAdvertAdapter extends FirebaseRecyclerAdapter<Advert, SearchAdvertAdapter.ViewHolder> {
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("customers");
    private ArrayList<Advert> adverts;
    private String id;
    private Context context;

    public SearchAdvertAdapter(@NonNull FirebaseRecyclerOptions<Advert> options, ArrayList<Advert> adverts, String id, Context context) {
        super(options);
        this.adverts = adverts;
        this.id = id;
        this.context = context;
    }

    public SearchAdvertAdapter(@NonNull FirebaseRecyclerOptions<Advert> options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_item, parent, false);
        context = context.getApplicationContext();
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Advert model) {
        Double p = adverts.get(position).getPrice();
        NumberFormat format = NumberFormat.getCurrencyInstance();

        holder.advertName.setText(adverts.get(position).getName());
        holder.advertPrice.setText(format.format(p));
        holder.categoryBtn.setText(adverts.get(position).getCategory());
        holder.advertDescription.setText(adverts.get(position).getDescription());

        reference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer = snapshot.getValue(Customer.class);
                String name = customer.getFirstname() + " " + customer.getLastname();
                holder.userName.setText(name);
                Glide.with(context).load(customer.getImage()).into(holder.userPP);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Data couldn't fetched", Toast.LENGTH_SHORT).show();
            }
        });

        Glide.with(context).load(adverts.get(position).getImage()).into(holder.advertImage);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView advertName, advertPrice, userName, advertDescription;
        private final ImageView advertImage, userPP;
        private final MaterialButton categoryBtn, userMessageBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            advertName = itemView.findViewById(R.id.advert_name);
            advertPrice = itemView.findViewById(R.id.advert_price);
            advertImage = itemView.findViewById(R.id.advert_image);
            userPP = itemView.findViewById(R.id.advert_user_pp);
            userName = itemView.findViewById(R.id.advert_post_user_name);
            categoryBtn = itemView.findViewById(R.id.advert_category_btn);
            userMessageBtn = itemView.findViewById(R.id.advert_user_message_btn);
            advertDescription = itemView.findViewById(R.id.advert_post_description);

            itemView.setTag(itemView);
        }
    }
}
