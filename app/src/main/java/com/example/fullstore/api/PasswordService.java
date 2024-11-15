package com.example.fullstore.api;

import com.example.fullstore.models.NewPasswordRequest;
import com.example.fullstore.models.ResetPasswordRequest;
import com.example.fullstore.models.ResetPasswordResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PasswordService {

    @POST("password/reset-password-request")
    Call<ResetPasswordResponse> requestPasswordReset(@Body ResetPasswordRequest resetPasswordRequest);

    @POST("password/reset-password/{uid}/{token}/")
    Call<ResetPasswordResponse> newPassword(@Path("uid") String uid,
                                            @Path("token") String token,
                                            @Body NewPasswordRequest newPasswordRequest);
}
