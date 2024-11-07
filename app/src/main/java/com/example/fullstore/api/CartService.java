package com.example.fullstore.api;

import com.example.fullstore.models.AddProductRequest;
import com.example.fullstore.models.AddProductResponse;
import com.example.fullstore.models.Cart;
import com.example.fullstore.models.RemoveProductRequest;
import com.example.fullstore.models.RemoveProductResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CartService {

    @GET("cart/get_cart")
    Call<Cart> getCart();

    @POST("cart/add_product")
    Call<AddProductResponse> addProductToCart(@Body AddProductRequest addProductRequest);

    @POST("cart/remove_item")
    Call<RemoveProductResponse> removeProductFromCart(@Body RemoveProductRequest removeProductRequest);

    @POST("cart/create_cart")
    Call<Cart> createCart();
}