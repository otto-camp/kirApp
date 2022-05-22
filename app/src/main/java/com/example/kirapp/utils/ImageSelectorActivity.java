package com.example.kirapp.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kirapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ImageSelectorActivity extends AppCompatActivity {
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private MaterialButton selectImageBtn;
    private ImageView selectImage;
    private Uri imageUri;
    private StorageReference storageReference = storage.getReference();
    private final ActivityResultLauncher<Intent> startForResult = getStartForResult();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);
        selectImage = findViewById(R.id.select_image);
        selectImageBtn = findViewById(R.id.select_image_btn);

        selectImageBtn.setOnClickListener(s -> selectImage());
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

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String fileName = format.format(new Date()) + "?" + user.getEmail();

                storageReference = FirebaseStorage.getInstance().getReference("images/" + fileName);
                storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    selectImage.setImageURI(imageUri);
                    Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}