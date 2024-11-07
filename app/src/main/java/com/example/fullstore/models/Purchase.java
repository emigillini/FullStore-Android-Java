package com.example.fullstore.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Purchase {
    private String _id;
    private User user;
    private int total;
    private String paymentType;
    private Cart cart;
    private String invoice_number;
    private String date;
    private Delivery delivery;

    public Purchase(String _id, User user, int total, String paymentType, Cart cart, String invoice_number, String date, Delivery delivery) {
        this._id = _id;
        this.user = user;
        this.total = total;
        this.paymentType = paymentType;
        this.cart = cart;
        this.invoice_number = invoice_number;
        this.date = date;
        this.delivery = delivery;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getFormattedDate() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Formato con milisegundos
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a"); // Formato amigable

        try {
            Date parsedDate = inputFormat.parse(date);
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid date";
        }
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public String getDate() {
        return date;
    }


}
