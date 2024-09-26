package com.example.licenseactivity;

public class User {
    private String email;
    private String phone;

    public User() {
        // Constructor gol necesar pentru Firestore
    }

    public User(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}