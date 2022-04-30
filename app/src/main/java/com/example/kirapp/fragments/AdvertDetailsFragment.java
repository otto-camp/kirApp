package com.example.kirapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kirapp.databinding.FragmentAdvertDetailsBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class AdvertDetailsFragment extends Fragment {
    private TextView advertName, advertLikeCount, advertTimestamp, advertUsername, advertDescription;
    private MaterialButton categoryBtn, subCategoryBtn, advertMessageBtn, advertLikebtn, advertBookmarkBtn, advertShareBtn;
    private ImageView advertImage;
    private ShapeableImageView userImage;
    private FragmentAdvertDetailsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdvertDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}