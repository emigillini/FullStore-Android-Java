package com.example.fullstore.models;

import com.google.gson.annotations.SerializedName;

public class RemoveProductResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("cart")
    private Cart cart;

    public RemoveProductResponse(String message, Cart cart) {
        this.message = message;
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
