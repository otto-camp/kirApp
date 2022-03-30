package com.example.kirapp.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Admin {
    private final DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference("admins");
    private String id;
    private String email;
    private String password;


    public Admin() {
    }

    public Admin(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;
        Admin admin = (Admin) o;
        return getId().equals(admin.getId()) && getEmail().equals(admin.getEmail()) && getPassword().equals(admin.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword());
    }

    public String getId() {
        adminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return id;
    }

    public void setId(String id) {
        this.id = String.valueOf(adminRef.setValue(id));
    }

    public String getEmail() {
        adminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return email;
    }

    public void setEmail(String email) {
        this.email = String.valueOf(adminRef.setValue(email));
    }

    public String getPassword() {
        adminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                password = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return password;
    }

    public void setPassword(String password) {
        this.password = String.valueOf(adminRef.setValue(password));
    }
}
