package com.example.fullstore.api;

import com.example.fullstore.models.AddConversationRequest;
import com.example.fullstore.models.AddMessageRequest;
import com.example.fullstore.models.Conversations;
import com.example.fullstore.models.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MessagingService {

    @GET("conversation/user")
    Call<List<Conversations>> getUserConversations();

    @POST("conversation")
    Call<Conversations> createConversation(@Body AddConversationRequest addConversationRequest);

    @GET("conversation/{conversationId}")
    Call<Conversations> getConversationById(@Path("conversationId") String conversationId);

    @POST("message")
    Call<Message> sendMessage(@Body AddMessageRequest addMessageRequest);

    @GET("message")
    Call<List<Message>> getMessagesByConversation(@Query("conversation") String conversationId);
}