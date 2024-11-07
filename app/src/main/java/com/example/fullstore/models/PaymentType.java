package com.example.fullstore.models;

public class PaymentType {
    private String _id;
    private String description;

    public PaymentType(String _id, String description) {
        this._id = _id;
        this.description = description;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }
}
