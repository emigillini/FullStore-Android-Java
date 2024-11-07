package com.example.fullstore.repository;

import android.content.Context;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.api.PurchaseService;
import com.example.fullstore.api.RetrofitClient;
import com.example.fullstore.models.PurchaseConfirmRequest;
import com.example.fullstore.models.PurchaseConfirmResponse;
import com.example.fullstore.models.SimplePurchase;

import java.util.List;

import retrofit2.Call;

public class PurchaseRepository {
    private PurchaseService purchaseService;

    public PurchaseRepository(SessionManager sessionManager, Context context) {
        purchaseService = RetrofitClient.getRetrofit(sessionManager, context).create(PurchaseService.class);
    }

    public Call<List<SimplePurchase>> getUserPurchases() {
        return purchaseService.getUserPurchases();
    }

    public Call<PurchaseConfirmResponse> purchaseConfirmRequest(String paymentType) {
        return purchaseService.confirmPurchase(new PurchaseConfirmRequest(paymentType));
    }


}
