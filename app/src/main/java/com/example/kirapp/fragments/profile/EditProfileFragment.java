package com.example.kirapp.fragments.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.kirapp.R;
import com.example.kirapp.models.Customer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class EditProfileFragment extends Fragment {
    private FirebaseUser user;
    private FirebaseStorage storage;
    private ShapeableImageView userImage;
    private MaterialButton imageEditBtn, birthdateEditBtn, updateBtn;
    private TextInputEditText etFname, etLname, etPhone, etGender;
    private DatabaseReference reference;
    private Customer customer;
    private StorageReference storageReference;
    private Uri imageUri;
    private final ActivityResultLauncher<Intent> startForResult = getStartForResult();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("customers");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        userImage = view.findViewById(R.id.ep_image);
        imageEditBtn = view.findViewById(R.id.ep_image_edit);
        birthdateEditBtn = view.findViewById(R.id.epet_birthdate);
        updateBtn = view.findViewById(R.id.ep_submit);
        etFname = view.findViewById(R.id.epet_fname);
        etLname = view.findViewById(R.id.epet_lname);
        etPhone = view.findViewById(R.id.epet_phone);
        etGender = view.findViewById(R.id.epet_gender);

        getData();
        imageEditBtn.setOnClickListener(v -> selectImage());
        birthdateEditBtn.setOnClickListener(this::datePicker);
        updateBtn.setOnClickListener(this::updateUser);
        return view;
    }


    private void getData() {
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customer = snapshot.getValue(Customer.class);

                Glide.with(requireContext()).load(customer.getImage()).into(userImage);
                birthdateEditBtn.setText(customer.getBirthDate());
                etFname.setText(customer.getFirstname());
                etLname.setText(customer.getLastname());
                etPhone.setText(customer.getPhoneNumber());
                etGender.setText(customer.getGender());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Data couldn't fetched", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser(View view) {
        customer.setFirstname(etFname.getText().toString());
        customer.setLastname(etLname.getText().toString());
        customer.setBirthDate(birthdateEditBtn.getText().toString());
        customer.setGender(etGender.getText().toString());
        customer.setPhoneNumber(etPhone.getText().toString());
        customer.setUpdatedAt(LocalDate.now().toString());
        reference.child(user.getUid()).setValue(customer);
    }

    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startForResult.launch(intent);
    }

    public ActivityResultLauncher<Intent> getStartForResult() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                imageUri = Objects.requireNonNull(result.getData()).getData();

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String fileName = format.format(new Date()) + "?pp:" + user.getEmail();

                storageReference = FirebaseStorage.getInstance().getReference("images/" + fileName);
                storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    userImage.setImageURI(imageUri);
                    StorageReference dRef = FirebaseStorage.getInstance().getReference("images/" + fileName);
                    dRef.getDownloadUrl().addOnSuccessListener(uri -> customer.setImage(uri.toString()));
                });
            }
        });
    }

    private void datePicker(View view) {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(R.string.select_date);
        final MaterialDatePicker<Long> picker = builder.build();
        birthdateEditBtn.setOnClickListener(v -> picker.show((getChildFragmentManager()), "DATE_PICKER"));
        picker.addOnPositiveButtonClickListener(selection -> birthdateEditBtn.setText(picker.getHeaderText()));
    }

}
