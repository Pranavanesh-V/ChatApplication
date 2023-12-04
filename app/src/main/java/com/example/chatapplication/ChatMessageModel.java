package com.example.chatapplication;

import com.google.firebase.Timestamp;

public class ChatMessageModel
{
    private String Message;
    private String senderId;
    private Timestamp timestamp;
    private String TextOwner;

    public String getTextOwner() {
        return TextOwner;
    }

    public void setTextOwner(String textOwner) {
        TextOwner = textOwner;
    }

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

    public ChatMessageModel(String message, String senderId, Timestamp timestamp,String TextOwner) {
        this.Message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.TextOwner=TextOwner;
    }
}
