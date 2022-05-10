package com.example.kirapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kirapp.R;
import com.example.kirapp.fragments.AdvertDetailsFragment;
import com.example.kirapp.models.Advert;
import com.example.kirapp.utils.ItemListener;
import com.google.android.material.card.MaterialCardView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.ViewHolder> {
    private final ArrayList<Advert> adverts;
    private final ItemListener itemListener;
    private Context context;

    public AdvertAdapter(ArrayList<Advert> adverts, Context context, ItemListener itemListener) {
        this.adverts = adverts;
        this.context = context;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view, itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Double p = adverts.get(position).getPrice();
        NumberFormat format = NumberFormat.getCurrencyInstance();

        holder.advertId.setText(adverts.get(position).getId());
        holder.advertName.setText(adverts.get(position).getName());
        holder.advertPrice.setText(format.format(p));
        holder.advertImage.setImageResource(R.mipmap.ic_blue_2);

        AdvertDetailsFragment advertDetailsFragment = new AdvertDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("AdvertId", holder.advertId.getText().toString());
        advertDetailsFragment.setArguments(bundle);
    }

    @Override
    public int getItemCount() {
        return adverts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView advertName, advertPrice, advertId;
        private final ImageView advertImage;
        private final MaterialCardView advertCard;
        private final ItemListener itemListener;

        public ViewHolder(@NonNull View itemView, ItemListener itemListener) {
            super(itemView);
            this.itemListener = itemListener;
            advertName = itemView.findViewById(R.id.advert_name);
            advertPrice = itemView.findViewById(R.id.advert_price);
            advertImage = itemView.findViewById(R.id.advert_image);
            advertCard = itemView.findViewById(R.id.advert_card);
            advertId = itemView.findViewById(R.id.advert_advert_id);

            advertCard.setOnClickListener(this);
            itemView.setTag(itemView);
        }

        @Override
        public void onClick(View view) {
            itemListener.response(getAbsoluteAdapterPosition(), "123");
        }
    }
}
