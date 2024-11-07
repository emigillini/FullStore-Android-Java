package com.example.fullstore.models;

import java.util.List;

public class Cart {

    private String id;
    private User user;
    private String date;
    private List<CartProduct> products;

    public Cart(String id, User user, String date, List<CartProduct> products) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartProduct> getProducts() {
        return products;
    }


}
