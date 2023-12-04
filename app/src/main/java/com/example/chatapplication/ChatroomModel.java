package com.example.chatapplication;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatroomModel
{
    String chatroomId;
    List<String> UserID;
    com.google.firebase.Timestamp lastMessageTimestamp;
    String lastMessageSenderId;

    public ChatroomModel()
    {

    }

    public Timestamp getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public ChatroomModel(String chatroomId, List<String> userID, com.google.firebase.Timestamp lastMessageTimestamp, String lastMessageSenderId) {
        this.chatroomId = chatroomId;
        UserID = userID;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public List<String> getUserID() {
        return UserID;
    }

    public void setUserID(List<String> userID) {
        UserID = userID;
    }


    public void setLastMessageTimestamp(com.google.firebase.Timestamp lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }
}
