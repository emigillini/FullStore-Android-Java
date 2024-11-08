package com.example.fullstore.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String getDate() {
        return date;
    }
}
