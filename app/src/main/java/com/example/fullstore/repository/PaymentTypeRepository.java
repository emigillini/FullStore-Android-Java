package com.example.fullstore.repository;

import android.content.Context;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.api.PaymentService;
import com.example.fullstore.api.RetrofitClient;
import com.example.fullstore.models.PaymentType;

import java.util.List;

import retrofit2.Call;

public class PaymentTypeRepository {
    private PaymentService paymentService;

    public PaymentTypeRepository(SessionManager sessionManager, Context context) {
        paymentService = RetrofitClient.getRetrofit(sessionManager, context).create(PaymentService.class);
    }

    public Call<List<PaymentType>> getPaymentMethods() {

        return paymentService.getPaymentMethods();
    }
}
