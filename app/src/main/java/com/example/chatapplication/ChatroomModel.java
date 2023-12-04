package com.example.chatapplication;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatroomModel
{
    String chatroomId;
    List<String> UserID;
    com.google.firebase.Timestamp lastMessageTimestamp;
    String lastMessageSenderId;
    String lastMessage;

    public ChatroomModel()
    {

    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Timestamp getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public ChatroomModel(String chatroomId, List<String> userID, com.google.firebase.Timestamp lastMessageTimestamp, String lastMessageSenderId,String lastMessage) {
        this.chatroomId = chatroomId;
        this.UserID = userID;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.lastMessageSenderId = lastMessageSenderId;
        this.lastMessage=lastMessage;
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
