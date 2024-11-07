package com.example.fullstore.repository;

import android.content.Context;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.api.PasswordService;
import com.example.fullstore.api.RetrofitClient;
import com.example.fullstore.models.NewPasswordRequest;
import com.example.fullstore.models.ResetPasswordRequest;
import com.example.fullstore.models.ResetPasswordResponse;

import retrofit2.Call;

public class PasswordRepository {
    private PasswordService passwordService;

    public PasswordRepository(SessionManager sessionManager, Context context) {
        passwordService = RetrofitClient.getRetrofit(sessionManager, context).create(PasswordService.class);
    }

    public Call<ResetPasswordResponse> requestPasswordReset(ResetPasswordRequest resetPasswordRequest) {
        return passwordService.requestPasswordReset(resetPasswordRequest);
    }

    public Call<ResetPasswordResponse> newPassword(String uid, String token, NewPasswordRequest newPasswordRequest) {
        return passwordService.newPassword(uid, token, newPasswordRequest);
    }


}
