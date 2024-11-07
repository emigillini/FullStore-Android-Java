package com.example.fullstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {

    @SerializedName("_id")
    private String _id;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("identification_number")
    private long identificationNumber;

    @SerializedName("password")
    private String password;

    @SerializedName("is_admin")
    private Boolean isAdmin;

    @SerializedName("phone")
    private String phone;

    @SerializedName("adress")
    private String adress;

    @SerializedName("last_connection")
    private Date lastConnection;


    public User() {
    }

    public User(String username, String email, String password, String adress, long identificationNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = false;
        this.identificationNumber = identificationNumber;
        this.phone = "111111";
        this.adress = adress;
        this.lastConnection = new Date();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getIdentificationNumber() {
        return identificationNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdress() {
        return adress;
    }

}
