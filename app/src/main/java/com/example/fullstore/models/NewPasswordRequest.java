package com.example.fullstore.models;

public class NewPasswordRequest {
    private String new_password;
    private String confirm_password;

    public NewPasswordRequest(String new_password, String confirm_password) {
        this.new_password = new_password;
        this.confirm_password = confirm_password;
    }

}
