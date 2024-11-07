package com.example.fullstore.models;

public class PurchaseConfirmResponse {

    private String message;
    private Purchase purchase;
    private Delivery delivery;

    public PurchaseConfirmResponse(String message, Purchase purchase, Delivery delivery) {
        this.message = message;
        this.purchase = purchase;
        this.delivery = delivery;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Purchase getPurchase() {
        return purchase;
    }


    public Delivery getDelivery() {
        return delivery;
    }


}
