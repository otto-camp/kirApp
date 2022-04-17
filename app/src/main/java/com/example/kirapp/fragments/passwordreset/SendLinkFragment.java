package com.example.kirapp.fragments.passwordreset;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.activities.LoginActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class SendLinkFragment extends Fragment {
    private FirebaseAuth auth;
    private MaterialButton resetPasswordBtn, backBtn;
    private TextInputEditText etEmail;

    public SendLinkFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_link, container, false);
        etEmail = view.findViewById(R.id.fp_email);
        resetPasswordBtn = view.findViewById(R.id.fp_btn);
        backBtn = view.findViewById(R.id.back_btn);
        String email = Objects.requireNonNull(etEmail.getText()).toString();
        resetPasswordBtn.setOnClickListener(view1 -> sendLink(email));
        backBtn.setOnClickListener(getOnClickListener());

        return view;
    }

    @NonNull
    private View.OnClickListener getOnClickListener() {
        return v -> startActivity(new Intent(v.getContext(), LoginActivity.class));
    }

    private void sendLink(String email) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireActivity().getApplicationContext(),
                    R.string.email_empty, Toast.LENGTH_SHORT).show();
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(requireActivity().getApplicationContext(),
                        R.string.fp_link_send, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(requireActivity().getApplicationContext(),
                        R.string.fp_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}