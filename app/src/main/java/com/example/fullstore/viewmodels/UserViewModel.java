package com.example.fullstore.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.models.LoginResponse;
import com.example.fullstore.models.LogoutResponse;
import com.example.fullstore.models.NewPasswordRequest;
import com.example.fullstore.models.ResetPasswordRequest;
import com.example.fullstore.models.ResetPasswordResponse;
import com.example.fullstore.models.User;
import com.example.fullstore.repository.PasswordRepository;
import com.example.fullstore.repository.UserRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    public UserRepository userRepository;
    private SessionManager sessionManager;
    private MutableLiveData<String> resetPasswordResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<LoginResponse> loginResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> logoutLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> registrationSuccessLiveData = new MutableLiveData<>(); // LiveData para el registro
    private CartViewModel cartViewModel;
    public PasswordRepository passwordRepository;

    public UserViewModel() {
        cartViewModel = new CartViewModel();

    }

    public void setSessionManager(SessionManager sessionManager, Context context) {
        this.sessionManager = sessionManager;
        this.userRepository = new UserRepository(sessionManager, context);
        this.passwordRepository = new PasswordRepository(sessionManager, context);
        cartViewModel.setSessionManager(sessionManager, context);
    }

    public LiveData<String> getPasswordResponseLiveData() {
        return resetPasswordResponseLiveData;
    }

    public LiveData<LoginResponse> getLoginResponseLiveData() {
        return loginResponseLiveData;
    }

    public LiveData<Boolean> getRegistrationSuccessLiveData() {
        return registrationSuccessLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLogoutLiveData() { // Método para obtener la LiveData de logout
        return logoutLiveData;
    }

    public void login(String email, String password) {
        userRepository.login(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    sessionManager.saveAuthToken(response.body().getToken());
                    sessionManager.setIsAdmin(response.body().getUser().getIsAdmin());
                    sessionManager.setUsername(response.body().getUser().getUsername());
                    sessionManager.saveUserId(response.body().getUser().get_id());
                    cartViewModel.createCart();

                    loginResponseLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Login failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void logout() {
        String token = sessionManager.getAuthToken();
        if (token == null) {
            errorLiveData.postValue("Token has expired or is unavailable");
            return;
        }
        if (token != null) {
            userRepository.logout().enqueue(new Callback<LogoutResponse>() {
                @Override
                public void onResponse(@NonNull Call<LogoutResponse> call, @NonNull Response<LogoutResponse> response) {
                    if (response.isSuccessful()) {
                        sessionManager.clearSession();
                        loginResponseLiveData.postValue(null);
                        logoutLiveData.postValue(true);
                    } else {
                        errorLiveData.postValue("Token unavailable for logout");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LogoutResponse> call, @NonNull Throwable t) {
                    errorLiveData.postValue("Network error: " + t.getMessage());
                }
            });
        }
    }

    public void register(String username, String email, String password, String address, long identification_number) {
        userRepository.register(username, email, password, address, identification_number).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    registrationSuccessLiveData.postValue(true);
                } else {
                    errorLiveData.postValue("Registration failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void requestPasswordReset(String email) {
        passwordRepository.requestPasswordReset(new ResetPasswordRequest(email)).enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResetPasswordResponse> call, @NonNull Response<ResetPasswordResponse> response) {
                if (response.isSuccessful()) {
                    resetPasswordResponseLiveData.setValue("A password reset link has been sent to your email.");
                } else {
                    errorLiveData.setValue("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResetPasswordResponse> call, @NonNull Throwable t) {
                errorLiveData.setValue("Error: " + t.getMessage());
            }
        });
    }

    public void resetPassword(String uid, String token, NewPasswordRequest newPasswordRequest) {
        passwordRepository.newPassword(uid, token, newPasswordRequest).enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(@NonNull Call<ResetPasswordResponse> call, @NonNull Response<ResetPasswordResponse> response) {
                if (response.isSuccessful()) {
                    resetPasswordResponseLiveData.setValue("Password successfully reset.");
                } else {
                    errorLiveData.setValue("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResetPasswordResponse> call, @NonNull Throwable t) {
                errorLiveData.setValue("Error: " + t.getMessage());
            }
        });
    }

}



