package com.example.kirapp.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.fragments.ProfileFragment;
import com.example.kirapp.models.CustomerCredentials;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class CredentialsFragment extends Fragment {
    private final CustomerCredentials credentials = new CustomerCredentials();
    private TextInputEditText etIdentity, etCity, etCounty;
    private MaterialButton submitBtn;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("customers");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credentials, container, false);
        etIdentity = view.findViewById(R.id.c_identity);
        etCity = view.findViewById(R.id.c_city);
        etCounty = view.findViewById(R.id.c_county);
        submitBtn = view.findViewById(R.id.c_submit);

        submitBtn.setOnClickListener(v -> {
            credentials.setUid(user.getUid());
            credentials.setIdentityNumber(Objects.requireNonNull(etIdentity.getText()).toString());
            credentials.setCity(Objects.requireNonNull(etCity.getText()).toString());
            credentials.setCounty(Objects.requireNonNull(etCounty.getText()).toString());

            reference.child(user.getUid()).child("credentials").setValue(credentials);

            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new ProfileFragment()).commit();
        });
        return view;
    }


}