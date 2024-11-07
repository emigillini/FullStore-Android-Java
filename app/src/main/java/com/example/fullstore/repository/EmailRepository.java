package com.example.fullstore.repository;

import android.content.Context;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.api.EmailService;
import com.example.fullstore.api.RetrofitClient;
import com.example.fullstore.models.EmailRequest;


import retrofit2.Call;

public class EmailRepository {
    private EmailService emailService;

    public EmailRepository(SessionManager sessionManager, Context context) {
        emailService = RetrofitClient.getRetrofit(sessionManager, context).create(EmailService.class);
    }

    public Call<Void> sendEmail(EmailRequest emailRequest) {

        return emailService.sendEmail(emailRequest);
    }
}
