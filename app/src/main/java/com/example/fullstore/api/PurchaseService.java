package com.example.fullstore.api;

import com.example.fullstore.models.PurchaseConfirmRequest;
import com.example.fullstore.models.PurchaseConfirmResponse;
import com.example.fullstore.models.SimplePurchase;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PurchaseService {

    @POST("purchase/confirm_purchase")
    Call<PurchaseConfirmResponse> confirmPurchase(@Body PurchaseConfirmRequest purchaseConfirmRequest);

    @GET("purchase/user_purchases")
    Call<List<SimplePurchase>> getUserPurchases();
}
