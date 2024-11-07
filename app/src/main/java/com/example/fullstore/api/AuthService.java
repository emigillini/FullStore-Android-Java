package com.example.fullstore.api;

import com.example.fullstore.models.LoginRequest;
import com.example.fullstore.models.LoginResponse;
import com.example.fullstore.models.LogoutResponse;
import com.example.fullstore.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("users/register")
    Call<User> registerUser(@Body User user);

    @POST("users/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("users/logout")
    Call<LogoutResponse> logoutUser();
}
