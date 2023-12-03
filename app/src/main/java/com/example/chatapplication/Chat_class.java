package com.example.chatapplication;

public class Chat_class
{
    String Username;
    String Message;
    String Time;

    public String getUsername() {
        return Username;
    }

    public String getMessage() {
        return Message;
    }

    public String getTime() {
        return Time;
    }

    public Chat_class(String username, String message, String time) {
        this.Username = username;
        this.Message = message;
        this.Time = time;
    }
}
