package com.example.chatapplication;

import android.content.Intent;


public class AndroidUtil
{
    public static void passUserModelAsIntent(Intent intent, UserModel model)
    {
        intent.putExtra("username",model.getUsername());
        intent.putExtra("phone",model.getPhone());
        intent.putExtra("userId",model.getUserId());
    }
}
