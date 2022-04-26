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

import java.text.NumberFormat;
import java.util.ArrayList;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.ViewHolder> {
    private final ArrayList<Advert> adverts;
    private final Context context;

    public AdvertAdapter(ArrayList<Advert> adverts, Context context) {
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
        Double p = adverts.get(position).getPrice();
        NumberFormat format = NumberFormat.getCurrencyInstance();

        holder.advertName.setText(adverts.get(position).getName());
        holder.advertPrice.setText(format.format(p));
        holder.advertImage.setImageResource(R.mipmap.ic_blue_2);
    }

    @Override
    public int getItemCount() {
        return adverts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView advertName;
        private final TextView advertPrice;
        private final ImageView advertImage;

        public ViewHolder(@NonNull View view) {
            super(view);
            advertName = view.findViewById(R.id.advert_name);
            advertPrice = view.findViewById(R.id.advert_price);
            advertImage = view.findViewById(R.id.advert_image);
            view.setTag(view);
        }

        @Override
        public void onClick(View view) {
            //GO TO DETAILS...
        }
    }
}
