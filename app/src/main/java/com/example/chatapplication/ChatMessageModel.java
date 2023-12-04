package com.example.chatapplication;

import com.google.firebase.Timestamp;

public class ChatMessageModel
{
    private String Message;
    private String senderId;
    private Timestamp timestamp;

    public ChatMessageModel()
    {

    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public ChatMessageModel(String message, String senderId, Timestamp timestamp) {
        Message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }
}
