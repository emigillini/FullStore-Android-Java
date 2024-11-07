package com.example.fullstore.models;

import com.google.gson.annotations.SerializedName;

public class AddProductResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("cart")
    private Cart cart;

    public AddProductResponse(String message, Cart cart) {
        this.message = message;
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
