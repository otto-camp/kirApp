package com.example.kirapp.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Customer {
    private final DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference("customers");
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;
    private String identityNumber;
    private LocalDate birthDate;
    //Advertle bağlantısı yapılacak
    //BURAYA FOTO ALMA GELECEK

    public Customer(String id, String firstname, String lastname, String email,
                    String password, String phoneNumber, String identityNumber, LocalDate birthDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.identityNumber = identityNumber;
        this.birthDate = birthDate;
    }

    public Customer() {
    }

    public String getId() {
        customerRef.addValueEventListener(new ValueEventListener() {
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
        this.id = String.valueOf(customerRef.setValue(id));
    }

    public String getFirstname() {
        customerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firstname = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = String.valueOf(customerRef.setValue(firstname));
    }

    public String getLastname() {
        customerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lastname = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return lastname;

    }

    public void setLastname(String lastname) {
        this.lastname = String.valueOf(customerRef.setValue(lastname));
    }

    public String getEmail() {
        customerRef.addValueEventListener(new ValueEventListener() {
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
        this.email = String.valueOf(customerRef.setValue(email));
    }

    public String getPassword() {
        customerRef.addValueEventListener(new ValueEventListener() {
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
        this.password = String.valueOf(customerRef.setValue(password));
    }

    public String getPhoneNumber() {
        customerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phoneNumber = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = String.valueOf(customerRef.setValue(phoneNumber));
    }

    public String getIdentityNumber() {
        customerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                identityNumber = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = String.valueOf(customerRef.setValue(identityNumber));
    }

    public LocalDate getBirthDate() {
        customerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String date = snapshot.getValue(String.class);
                birthDate = LocalDate.parse(date);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String date = birthDate.format(formatter);
        this.birthDate = LocalDate.parse(String.valueOf(customerRef.setValue(date)));
    }
}
