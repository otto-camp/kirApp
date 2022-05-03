package com.example.kirapp.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kirapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageSelectorActivity extends AppCompatActivity {
    private MaterialButton selectImageBtn, uploadImageBtn;
    private ImageView selectImage;
    private Uri imageUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);
        selectImage = findViewById(R.id.select_image);
        selectImageBtn = findViewById(R.id.select_image_btn);
        uploadImageBtn = findViewById(R.id.upload_image_btn);

        selectImageBtn.setOnClickListener(s -> selectImage());
        uploadImageBtn.setOnClickListener(u -> uploadImage());
    }

    public void uploadImage() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date date = new Date();
        String fileName = format.format(date);
        storageReference = FirebaseStorage.getInstance().getReference("images/" + fileName);

        storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            selectImage.setImageURI(null);
            Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
        });
    }

    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUri = data.getData();
            selectImage.setImageURI(imageUri);
        }
    }
}