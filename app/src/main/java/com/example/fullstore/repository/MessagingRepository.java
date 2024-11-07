package com.example.fullstore.repository;

import android.content.Context;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.api.MessagingService;
import com.example.fullstore.api.RetrofitClient;
import com.example.fullstore.models.AddConversationRequest;
import com.example.fullstore.models.AddMessageRequest;
import com.example.fullstore.models.Conversations;
import com.example.fullstore.models.Message;

import java.util.List;

import retrofit2.Call;

public class MessagingRepository {
    private MessagingService messagingService;

    public MessagingRepository(SessionManager sessionManager, Context context) {
        messagingService = RetrofitClient.getRetrofit(sessionManager, context).create(MessagingService.class);
    }

    public Call<List<Conversations>> getUserConversations() {
        return messagingService.getUserConversations();
    }

    public Call<Conversations> createConversation(AddConversationRequest addConversationRequest) {
        return messagingService.createConversation(addConversationRequest);
    }

    public Call<Conversations> getConversationById(String conversationId) {
        return messagingService.getConversationById(conversationId);
    }

    public Call<List<Message>> getMessagesByConversationId(String conversationId) {
        return messagingService.getMessagesByConversation(conversationId);
    }

    public Call<Message> sendMessage(AddMessageRequest addMessageRequest) {
        return messagingService.sendMessage(addMessageRequest);
    }


}
