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
import com.example.kirapp.models.Advert;

import java.util.List;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.ViewHolder> {
    private final List<Advert> adverts;
    private final Context context;

    public AdvertAdapter(List<Advert> adverts, Context context) {
        this.adverts = adverts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Advert advert = adverts.get(position);
        holder.advertName.setText(advert.getName());
        holder.advertPrice.setText(String.valueOf(advert.getPrice()));
        holder.advertImage.setImageResource(R.mipmap.ic_blue_1);
    }

    @Override
    public int getItemCount() {
        return adverts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView advertName;
        private final TextView advertPrice;
        private final ImageView advertImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            advertName = itemView.findViewById(R.id.advert_name);
            advertPrice = itemView.findViewById(R.id.advert_price);
            advertImage = itemView.findViewById(R.id.advert_image);
        }

        @Override
        public void onClick(View view) {
            //GO TO DETAILS...
        }
    }
}
