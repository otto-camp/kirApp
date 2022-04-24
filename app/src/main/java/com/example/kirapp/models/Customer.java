package com.example.kirapp.models;

import androidx.annotation.NonNull;

public class Customer {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String birthDate;
    private String phoneNumber;
    private String gender;
    private boolean status;
    private String createdAt;
    private String updatedAt;

    public Customer(String firstname, String lastname, String email, String birthDate,
                    String phoneNumber, String gender, boolean status,
                    String createdAt, String updatedAt) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Customer(String id, String firstname, String lastname, String email,
                    String birthDate, String phoneNumber, String gender,
                    boolean status, String createdAt, String updatedAt) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Customer() {

    }

    @NonNull
    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
