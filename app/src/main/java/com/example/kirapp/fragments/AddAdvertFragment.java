package com.example.kirapp.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.activities.LoginActivity;
import com.example.kirapp.models.Advert;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class AddAdvertFragment extends Fragment {
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("adverts");
    private final Advert advert = new Advert();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();
    private TextInputEditText etName, etDescription, etPrice;
    private MaterialButton submit, imageBtn;
    private Spinner categorySpinner;
    private ArrayAdapter<CharSequence> categoryAdapter;
    private Uri imageUri;
    private final ActivityResultLauncher<Intent> startForResult = getStartForResult();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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
        categorySpinner = view.findViewById(R.id.category_spinner);

        categoryAdapter = ArrayAdapter.createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(categoryItemSelectedListener());

        imageBtn.setOnClickListener(this::getImage);
        submit.setOnClickListener(this::submitAdvert);

        return view;
    }

    private void getImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startForResult.launch(intent);
    }

    public ActivityResultLauncher<Intent> getStartForResult() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                imageUri = Objects.requireNonNull(result.getData()).getData();
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    private AdapterView.OnItemSelectedListener categoryItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                advert.setCategory(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                advert.setCategory(adapterView.getItemAtPosition(0).toString());
            }
        };
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
        uploadImage();
        Toast.makeText(getContext(), R.string.advert_added, Toast.LENGTH_LONG).show();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MainPageFragment()).commit();
    }

    private void uploadImage() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = format.format(new Date()) + "?" + Objects.requireNonNull(user).getEmail();

        storageReference = FirebaseStorage.getInstance().getReference("images/" + fileName);
        storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            StorageReference dRef = FirebaseStorage.getInstance().getReference("images/" + fileName);
            dRef.getDownloadUrl().addOnSuccessListener(uri ->
                    databaseReference.child(user.getUid()).child(advert.getId()).child("image").setValue(uri.toString()));
        });
    }

}