package com.example.fullstore.models;

public class AddMessageRequest {
    private String conversationId;
    private String content;

    public AddMessageRequest(String conversationId, String content) {
        this.conversationId = conversationId;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
