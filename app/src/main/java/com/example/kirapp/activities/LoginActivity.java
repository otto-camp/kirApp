package com.example.kirapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kirapp.R;
import com.example.kirapp.models.Customer;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN";
    private static final int RC_SIGN_IN = 9001;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("customers");
    private TextInputEditText email, password;
    private FirebaseAuth auth;
    ActivityResultLauncher<Intent> startForResult = getIntentActivityResultLauncher();
    private GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        MaterialButton login = findViewById(R.id.login_btn);
        //MaterialButton loginGoogle = findViewById(R.id.login_google_btn);
        MaterialButton forgotPassword = findViewById(R.id.forgot_password_btn);
        MaterialButton register = findViewById(R.id.register_btn);
        login.setOnClickListener(this::signInWithEmail);
        register.setOnClickListener(this::register);
        forgotPassword.setOnClickListener(this::forgotPassword);
        //loginGoogle.setOnClickListener(this::signInWithGoogle);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, signInOptions);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            reload(currentUser);
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
            Log.d(TAG, "auth with google: " + account.getIdToken() + "/" + account.getDisplayName());
            authWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            Log.w(TAG, "Google login failed!", e);
        }
    }

    private void authWithGoogle(String idToken) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(authCredential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        // FIXME: 24.04.2022
                        String[] names = Objects.requireNonNull(Objects.requireNonNull(user).getDisplayName()).split(" ");
                        Customer customer = new Customer(user.getUid(), names[0], names[1], user.getEmail(), null,
                                null, null, true, LocalDate.now().toString(), LocalDate.now().toString());
                        databaseReference.child(user.getUid()).setValue(customer);
                        reload(user);
                    } else {
                        reload(null);
                    }
                });
    }

    private void reload(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_SHORT).show();
        } else if (user.getUid().equals("eV7ZvoMfJMdC7LWV1iDZuexoQU42")) {
            startActivity(new Intent(this, AdminDashboardActivity.class));
        } else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    @NonNull
    private ActivityResultLauncher<Intent> getIntentActivityResultLauncher() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (Objects.requireNonNull(result).getResultCode() == RC_SIGN_IN) {
                Intent intent = new Intent();
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                handleSignInResult(task);
            }
        });
    }

    private void signInWithGoogle(View view) {
        Intent intent = googleSignInClient.getSignInIntent();
        startForResult.launch(intent);
    }

    private void signInWithEmail(View view) {
        auth.signInWithEmailAndPassword(Objects.requireNonNull(email.getText()).toString(), Objects.requireNonNull(password.getText()).toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (Objects.requireNonNull(user).isEmailVerified()) {
                            reload(user);
                        } else if (email.getText().toString().equals("admin@gmail.com")) {
                            reload(user);
                        } else {
                            user.sendEmailVerification();
                            Toast.makeText(LoginActivity.this, R.string.email_not_verified, Toast.LENGTH_LONG).show();
                        }

                    } else {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}
