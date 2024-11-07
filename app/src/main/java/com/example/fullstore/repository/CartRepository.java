package com.example.fullstore.repository;

import android.content.Context;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.api.CartService;
import com.example.fullstore.api.RetrofitClient;
import com.example.fullstore.models.AddProductRequest;
import com.example.fullstore.models.AddProductResponse;
import com.example.fullstore.models.Cart;
import com.example.fullstore.models.RemoveProductRequest;
import com.example.fullstore.models.RemoveProductResponse;

import retrofit2.Call;

public class CartRepository {
    private CartService cartApiService;

    public CartRepository(SessionManager sessionManager, Context context) {
        cartApiService = RetrofitClient.getRetrofit(sessionManager, context).create(CartService.class);
    }

    public Call<Cart> getCart() {
        return cartApiService.getCart();
    }

    public Call<AddProductResponse> addProductToCart(String productId, int quantity) {
        return cartApiService.addProductToCart(new AddProductRequest(productId, quantity));
    }

    public Call<RemoveProductResponse> removeProductFromCart(String productId, int quantity) {
        return cartApiService.removeProductFromCart(new RemoveProductRequest(productId, quantity));
    }

    public Call<Cart> createCart() {
        return cartApiService.createCart();
    }
}
