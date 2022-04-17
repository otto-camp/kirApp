package com.example.kirapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kirapp.R;
import com.example.kirapp.fragments.MainPageFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    ActivityResultLauncher<Intent> startForResult = getIntentActivityResultLauncher();


    private EditText email, password;
    private FirebaseAuth auth;
    private BottomNavigationView bottomNavigationView;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        MaterialButton login = findViewById(R.id.login_btn);
        MaterialButton loginGoogle = findViewById(R.id.login_google_btn);
        MaterialButton forgotPassword = findViewById(R.id.forgot_password_btn);
        MaterialButton register = findViewById(R.id.register_btn);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(this::signInWithEmail);
        register.setOnClickListener(this::register);
        forgotPassword.setOnClickListener(this::forgotPassword);
        loginGoogle.setOnClickListener(this::signInWithGoogle);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, signInOptions);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            reload(firebaseUser);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            updateUI(null);
        }
    }

    private void reload(FirebaseUser user) {
        if (user == null) {
            bottomNavigationView.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.profileFragment) {
                    Fragment fragment = new MainPageFragment();
                }
                return true;
            });
        }
        // startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void updateUI(GoogleSignInAccount account) {

    }

    @NonNull
    private ActivityResultLauncher<Intent> getIntentActivityResultLauncher() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (Objects.requireNonNull(result).getResultCode() == RC_SIGN_IN) {
                Intent intent = null;
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                handleSignInResult(task);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void signInWithGoogle(View view) {
        Intent intent = googleSignInClient.getSignInIntent();
        startForResult.launch(intent);

    }

    private void signInWithEmail(View view) {
        auth.signInWithEmailAndPassword(
                email.getText().toString().trim(),
                password.getText().toString().trim()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                reload(Objects.requireNonNull(user));
            } else {
                Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_SHORT).show();
                reload(null);
            }
        });
    }

    private void register(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private void forgotPassword(View view) {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }
}
