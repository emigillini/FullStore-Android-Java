package com.example.fullstore.api;

import com.example.fullstore.models.UpdateUserRequest;
import com.example.fullstore.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;


public interface DashboardService {

    @GET("users/")
    Call<User> getUser();

    @PATCH("users/update")
    Call<User> updateUser(@Body UpdateUserRequest updateUserRequest);

}
