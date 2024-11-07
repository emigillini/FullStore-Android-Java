package com.example.fullstore.repository;

import android.content.Context;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.api.AuthService;
import com.example.fullstore.models.LoginRequest;
import com.example.fullstore.models.LoginResponse;

import retrofit2.Call;

import com.example.fullstore.api.RetrofitClient;
import com.example.fullstore.models.LogoutResponse;
import com.example.fullstore.models.User;

public class UserRepository {
    private AuthService authService;

    public UserRepository(SessionManager sessionManager, Context context) {
        authService = RetrofitClient.getRetrofit(sessionManager, context).create(AuthService.class);
    }

    public Call<LoginResponse> login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        return authService.loginUser(loginRequest);
    }

    public Call<User> register(String username, String email, String password, String address, long identification_number) {
        User user = new User(username, email, password, address, identification_number);
        return authService.registerUser(user);
    }

    public Call<LogoutResponse> logout() {

        return authService.logoutUser(); //
    }
}