package com.example.fullstore.models;


public class Delivery {
    private String _id;
    private String tracking_number;
    private String delivery_address;
    private String estimated_date;
    private String delivery_date;
    private String delivery_status;

    public Delivery(String _id, String tracking_number, String delivery_address, String estimated_date, String delivery_date, String delivery_status) {
        this._id = _id;

        this.tracking_number = tracking_number;
        this.delivery_address = delivery_address;
        this.estimated_date = estimated_date;
        this.delivery_date = delivery_date;
        this.delivery_status = delivery_status;
    }


    public String getTracking_number() {
        return tracking_number;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public String getEstimated_date() {
        return estimated_date;
    }


    public String getDelivery_date() {
        return delivery_date;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

}
