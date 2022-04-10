package com.example.kirapp.models;

import java.time.LocalDate;
import java.util.List;

public class Customer {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String birthDate;
    private String gender;
    private boolean status;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private List<Category> categories;

}
