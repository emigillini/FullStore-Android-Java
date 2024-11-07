package com.example.fullstore.api;

import com.example.fullstore.models.EmailRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmailService {

    @POST("send_mail/")
    Call<Void> sendEmail(@Body EmailRequest emailRequest);
}
