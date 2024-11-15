package com.example.fullstore.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.models.UpdateUserRequest;
import com.example.fullstore.models.User;
import com.example.fullstore.repository.DashBoardRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardViewModel extends ViewModel {
    private DashBoardRepository dashboardRepository;
    private SessionManager sessionManager;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> sessionExpiredLiveData = new MutableLiveData<>(false);

    public DashBoardViewModel() {
    }

    public void setSessionManager(SessionManager sessionManager, Context context) {
        this.sessionManager = sessionManager;
        this.dashboardRepository = new DashBoardRepository(sessionManager, context);
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getUpdateSuccess() {
        return updateSuccess;
    }

    public LiveData<Boolean> getSessionExpiredLiveData() {
        return sessionExpiredLiveData;
    }


    public void getUserData() {
        if (isSessionExpired()) {
            return;
        }
        dashboardRepository.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userLiveData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Error retrieving user data");
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                errorLiveData.setValue("Connection failed: " + t.getMessage());
            }
        });
    }

    public void updateUser(UpdateUserRequest updateUserRequest) {
        if (isSessionExpired()) {
            return; // The session is already marked as expired
        }
        dashboardRepository.updateUser(updateUserRequest).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    userLiveData.setValue(response.body());
                    updateSuccess.setValue(true);
                } else {
                    errorLiveData.setValue("Error updating user data");
                    updateSuccess.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                errorLiveData.setValue("Connection failed: " + t.getMessage());
                updateSuccess.setValue(false);
            }
        });
    }

    private boolean isSessionExpired() {
        if (sessionManager.isTokenExpired()) {
            errorLiveData.setValue("Session expired");
            sessionExpiredLiveData.setValue(true);
            return true;
        }
        return false;
    }

}