package com.example.kirapp.adapters.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kirapp.R;
import com.example.kirapp.models.Advert;

import java.util.ArrayList;

public class ProfileAdvertAdapter extends RecyclerView.Adapter<ProfileAdvertAdapter.ViewHolder> {
    private final ArrayList<Advert> adverts;
    private Context context;

    public ProfileAdvertAdapter(ArrayList<Advert> adverts, Context context) {
        this.adverts = adverts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_advert_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(adverts.get(position).getImage()).into(holder.advertImage);
        holder.tName.setText(adverts.get(position).getName());
        holder.tPrice.setText(String.valueOf(adverts.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return adverts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView advertImage;
        private final TextView tName;
        private final TextView tPrice;

        public ViewHolder(View view) {
            super(view);
            advertImage = view.findViewById(R.id.profile_advert_image);
            tName = view.findViewById(R.id.profile_advert_name);
            tPrice = view.findViewById(R.id.profile_advert_price);
        }
    }
}
