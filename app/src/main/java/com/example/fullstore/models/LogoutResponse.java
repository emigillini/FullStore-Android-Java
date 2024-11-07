package com.example.fullstore.models;

import com.google.gson.annotations.SerializedName;

public class LogoutResponse {
    @SerializedName("user")
    private User user;

    @SerializedName("message")
    private String message;

    public LogoutResponse(User user, String message) {
        this.user = user;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}