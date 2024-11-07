package com.example.fullstore.repository;

import android.content.Context;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.api.DashboardService;
import com.example.fullstore.api.RetrofitClient;
import com.example.fullstore.models.UpdateUserRequest;
import com.example.fullstore.models.User;

import retrofit2.Call;

public class DashBoardRepository {

    private DashboardService dashboardService;

    public DashBoardRepository(SessionManager sessionManager, Context context) {
        dashboardService = RetrofitClient.getRetrofit(sessionManager, context).create(DashboardService.class);
    }

    public Call<User> getUser() {
        return dashboardService.getUser();
    }

    public Call<User> updateUser(UpdateUserRequest updateUserRequest) {
        return dashboardService.updateUser(updateUserRequest);
    }


}
