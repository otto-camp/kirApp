package com.example.kirapp.models;

import android.media.Image;

import androidx.annotation.NonNull;

public class Advert {
    private String id;
    private String name;
    private String createdAt;
    private String updatedAt;
    private String description;
    private double price;
    private boolean status;

    public Advert() {
    }

    public Advert(String id, String name, String createdAt, String updatedAt, String description, double price, boolean status) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    public Advert(String name, String createdAt, String updatedAt, String description, double price, boolean status, Image advertImage) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return "Advert{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
