package com.example.kirapp.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.fragments.AddAdvertFragment;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ImageSelectorActivity extends AppCompatActivity {
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private MaterialButton selectImageBtn, uploadImageBtn;
    private ImageView selectImage;
    private Uri imageUri;
    private final ActivityResultLauncher<Intent> startForResult = getStartForResult();

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
        //SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        // Date date = new Date();
        //String fileName = format.format(date);

        Bundle bundle = new Bundle();
        bundle.putString(user.getUid(), imageUri.toString());
        Fragment fragment = new AddAdvertFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.image_selector_activity, fragment).commit();

        //  storageReference = FirebaseStorage.getInstance().getReference("images/" + fileName);
        //  storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
        //      selectImage.setImageURI(imageUri);
        //      Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
        // });
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
                imageUri = result.getData().getData();
                selectImage.setImageURI(imageUri);
            }
        });
    }
}