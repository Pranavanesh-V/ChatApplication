package com.example.chatapplication;

import com.google.firebase.Timestamp;

public class UserModel
{
    private String phone;
    private String username;
    private Timestamp createdTimestamp;
    private String Email;
    private String Password;
    private String userId;

    public UserModel(String phone, String username, Timestamp createdTimestamp, String email, String password,String userId) {
        this.phone = phone;
        this.username = username;
        this.createdTimestamp = createdTimestamp;
        this.Email = email;
        this.Password = password;
        this.userId=userId;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    public UserModel()
    {

    }
}
