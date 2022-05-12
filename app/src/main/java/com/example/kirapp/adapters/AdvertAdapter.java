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
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.ViewHolder> {
    private final ArrayList<Advert> adverts;
    private Context context;

    public AdvertAdapter(ArrayList<Advert> adverts, Context context) {
        this.adverts = adverts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Double p = adverts.get(position).getPrice();
        NumberFormat format = NumberFormat.getCurrencyInstance();

        holder.advertName.setText(adverts.get(position).getName());
        holder.advertPrice.setText(format.format(p));
        holder.advertImage.setImageResource(R.mipmap.ic_blue_2);
        holder.userPP.setImageResource(R.mipmap.ic_blue_1);
        holder.categoryBtn.setText(adverts.get(position).getCategory());
        holder.subCategoryBtn.setText(adverts.get(position).getSubCategory());
    }

    @Override
    public int getItemCount() {
        return adverts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView advertName, advertPrice;
        private final ImageView advertImage, userPP;
        private final MaterialButton categoryBtn;
        private final MaterialButton subCategoryBtn;
        private final MaterialButton userMessageBtn;
        private final MaterialButton bookmarkBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            advertName = itemView.findViewById(R.id.advert_name);
            advertPrice = itemView.findViewById(R.id.advert_price);
            advertImage = itemView.findViewById(R.id.advert_image);
            userPP = itemView.findViewById(R.id.advert_user_pp);
            categoryBtn = itemView.findViewById(R.id.advert_category_btn);
            subCategoryBtn = itemView.findViewById(R.id.advert_subCategory_btn);
            userMessageBtn = itemView.findViewById(R.id.advert_user_message_btn);
            bookmarkBtn = itemView.findViewById(R.id.advert_bookmark);

            itemView.setTag(itemView);
        }
    }
}
