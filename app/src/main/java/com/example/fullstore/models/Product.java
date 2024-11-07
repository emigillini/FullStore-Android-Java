package com.example.fullstore.models;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("model")
    private String model;

    @SerializedName("brand")
    private Brand brand;

    @SerializedName("color")
    private String color;

    @SerializedName("size")
    private int size;

    @SerializedName("price")
    private double price;

    @SerializedName("stock")
    private int stock;

    @SerializedName("image")
    private String image;

    @SerializedName("detail")
    private String detail;

    @SerializedName("_id")
    private String _id;

    @SerializedName("id")
    private String id;

    public Product(String model, Brand brand, String color, int size, double price, int stock, String image, String detail, String _id, String id) {
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.size = size;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.detail = detail;
        this._id = _id;
        this.id = id;
    }

    // Getters and Setters
    public String get_id() {
        return _id;
    }

    public void set_id(String id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public static class Brand {
        @SerializedName("_id")
        private String _id;

        @SerializedName("description")
        private String description;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
