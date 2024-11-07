package com.example.fullstore.models;

public class UpdateUserRequest {

    private long identification_number;
    private String phone;
    private String adress;


    public UpdateUserRequest() {
    }


    public long getIdentification_number() {
        return identification_number;
    }

    public void setIdentification_number(long identification_number) {
        this.identification_number = identification_number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}

