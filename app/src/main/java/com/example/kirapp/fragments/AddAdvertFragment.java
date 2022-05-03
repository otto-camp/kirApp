package com.example.kirapp.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.activities.LoginActivity;
import com.example.kirapp.models.Advert;
import com.example.kirapp.utils.ImageSelectorActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Objects;

public class AddAdvertFragment extends Fragment {
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("adverts");
    private final Advert advert = new Advert();
    private TextInputEditText etName, etDescription, etPrice;
    private MaterialButton submit, imageBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (user == null) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_advert, container, false);
        etName = view.findViewById(R.id.advert_name);
        etDescription = view.findViewById(R.id.advert_description);
        etPrice = view.findViewById(R.id.advert_price);
        submit = view.findViewById(R.id.advert_add_button);
        imageBtn = view.findViewById(R.id.advert_image);
        imageBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), ImageSelectorActivity.class)));
        submit.setOnClickListener(this::submitAdvert);

        return view;
    }

    private void submitAdvert(View view) {
        advert.setId(databaseReference.push().getKey());
        advert.setName(Objects.requireNonNull(etName.getText()).toString());
        advert.setDescription(Objects.requireNonNull(etDescription.getText()).toString());
        advert.setPrice(Double.parseDouble(Objects.requireNonNull(etPrice.getText()).toString()));
        advert.setCreatedAt(LocalDate.now().toString());
        advert.setUpdatedAt(LocalDate.now().toString());
        advert.setStatus(true);
        databaseReference.child(Objects.requireNonNull(user).getUid()).child(advert.getId()).setValue(advert);
        Toast.makeText(getContext(), R.string.advert_added, Toast.LENGTH_LONG).show();
    }

}