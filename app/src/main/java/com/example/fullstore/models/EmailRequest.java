package com.example.fullstore.models;

public class EmailRequest {
    private String subject;
    private String message;
    private String toEmail;

    public EmailRequest(String subject, String message, String toEmail) {
        this.subject = subject;
        this.message = message;
        this.toEmail = toEmail;
    }
}
