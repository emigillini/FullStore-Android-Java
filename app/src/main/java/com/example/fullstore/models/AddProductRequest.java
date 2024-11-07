package com.example.fullstore.models;

public class AddProductRequest {
    private String product_id;
    private int quantity;

    public AddProductRequest(String product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

}
