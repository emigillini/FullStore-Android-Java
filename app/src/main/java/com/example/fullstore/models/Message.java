package com.example.fullstore.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String _id;

    private User user;
    private String content;
    private String created_at;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormattedMessageDate() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Formato con milisegundos
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a"); // Formato amigable

        try {
            Date parsedDate = inputFormat.parse(created_at);
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid date";
        }
    }
    public String getCreated_at() {
        return created_at;
    }
}