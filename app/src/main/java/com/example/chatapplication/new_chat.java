package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class new_chat extends AppCompatActivity {

    UserModel otherUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        otherUser=AndroidUtil.getUserModelFromIntent(getIntent());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(new_chat.this, Home_page.class);
        startActivity(intent);
        finish();
    }
}