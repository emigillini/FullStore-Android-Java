package com.example.fullstore.models;

public class RemoveProductRequest {
    private String product_id;
    private int quantity;

    public RemoveProductRequest(String product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

}
