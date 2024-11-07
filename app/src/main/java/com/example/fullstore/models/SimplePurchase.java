package com.example.fullstore.models;

public class SimplePurchase {
    private String id;
    private String username; // Para el usuario
    private double total;
    private String invoice_number;
    private String date;
    private Delivery delivery;

    public SimplePurchase(String id, String username, double total, String invoice_number, String date, Delivery delivery) {
        this.id = id;
        this.username = username;
        this.total = total;
        this.invoice_number = invoice_number;
        this.date = date;
        this.delivery = delivery;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public double getTotal() {
        return total;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public String getDate() {
        return date;
    }
}
