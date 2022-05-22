package com.example.kirapp.models;

public class CustomerCredentials {
    private String uid;
    private String identityNumber;
    private String city;
    private String county;

    public CustomerCredentials() {
    }

    public CustomerCredentials(String identityNumber, String city, String county) {
        this.identityNumber = identityNumber;
        this.city = city;
        this.county = county;
    }

    public CustomerCredentials(String uid, String identityNumber, String city, String county) {
        this.uid = uid;
        this.identityNumber = identityNumber;
        this.city = city;
        this.county = county;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}
