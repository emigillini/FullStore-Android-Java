package com.example.fullstore.api;

import com.example.fullstore.models.PaymentType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaymentService {

    @GET("payment")
    Call<List<PaymentType>> getPaymentMethods();
}
