package com.example.fullstore.models;

public class AddConversationRequest {
    private String name;

    public AddConversationRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
