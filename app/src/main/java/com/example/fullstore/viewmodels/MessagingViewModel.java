package com.example.fullstore.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.models.AddConversationRequest;
import com.example.fullstore.models.AddMessageRequest;
import com.example.fullstore.models.Conversations;
import com.example.fullstore.models.Message;
import com.example.fullstore.repository.MessagingRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagingViewModel extends ViewModel {
    private MessagingRepository messagingRepository;
    private SessionManager sessionManager;
    private MutableLiveData<List<Conversations>> userConversations = new MutableLiveData<>();
    private MutableLiveData<List<Message>> messages = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> sessionExpiredLiveData = new MutableLiveData<>(false);

    public MessagingViewModel() {

    }

    public void setSessionManager(SessionManager sessionManager, Context context) {
        this.sessionManager = sessionManager;
        this.messagingRepository = new MessagingRepository(sessionManager, context);
    }

    public LiveData<List<Conversations>> getUserConversationsLiveData() {

        return userConversations;
    }

    public LiveData<List<Message>> getMessagesByConversationIdLiveData(String conversationId) {

        return messages;
    }

    public LiveData<Boolean> getSessionExpiredLiveData() {
        return sessionExpiredLiveData;
    }

    public void loadUserConversations() {
        if (isSessionExpired()) {
            return;
        }
        messagingRepository.getUserConversations().enqueue(new Callback<List<Conversations>>() {
            @Override
            public void onResponse(Call<List<Conversations>> call, Response<List<Conversations>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Conversations> conversationsList = response.body();

                    Collections.reverse(conversationsList);

                    userConversations.setValue(conversationsList);
                } else {
                    errorLiveData.postValue("Error loading conversations");
                }
            }

            @Override
            public void onFailure(Call<List<Conversations>> call, Throwable t) {
                errorLiveData.setValue(t.getMessage());
            }
        });
    }


    public void loadMessagesByConversationId(String conversationId) {
        if (isSessionExpired()) {
            return;
        }
        messagingRepository.getMessagesByConversationId(conversationId).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    messages.setValue(response.body());

                } else {
                    errorLiveData.postValue("Error loading messagges");
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                errorLiveData.setValue(t.getMessage());
            }
        });
    }

    public void sendMessage(AddMessageRequest addMessageRequest) {
        if (isSessionExpired()) {
            return;
        }
        messagingRepository.sendMessage(addMessageRequest).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Message> currentMessages = messages.getValue();

                    if (currentMessages != null) {

                        currentMessages.add(response.body());
                        messages.setValue(currentMessages);
                    } else {
                        errorLiveData.postValue("Error sending messagge");
                    }
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                errorLiveData.setValue(t.getMessage());
            }
        });
    }

    public void createConversation(AddConversationRequest addConversationRequest) {
        if (isSessionExpired()) {
            return;
        }
        messagingRepository.createConversation(addConversationRequest).enqueue(new Callback<Conversations>() {
            @Override
            public void onResponse(Call<Conversations> call, Response<Conversations> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Conversations newConversation = response.body();


                    List<Conversations> currentConversations = userConversations.getValue();
                    if (currentConversations != null) {

                        currentConversations.add(0, newConversation);
                        userConversations.setValue(currentConversations);
                    } else {

                        List<Conversations> conversationsList = new ArrayList<>();
                        conversationsList.add(newConversation);
                        userConversations.setValue(conversationsList);
                    }
                }
            }

            @Override
            public void onFailure(Call<Conversations> call, Throwable t) {
                errorLiveData.setValue(t.getMessage());
            }
        });
    }

    private boolean isSessionExpired() {
        if (sessionManager.isTokenExpired()) {
            errorLiveData.setValue("Sesión expirada");
            sessionExpiredLiveData.setValue(true);
            return true;
        }
        return false;
    }

}

