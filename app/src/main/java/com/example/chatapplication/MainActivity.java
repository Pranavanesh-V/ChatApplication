package com.example.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            Handler handler = new Handler();
            handler.postDelayed(this::r, 1200); // Delay in milliseconds (1.20 seconds in this example)
    }
    public void r()
    {
        if (isUserLoggedIn())
        {
            Intent intent=new Intent(MainActivity.this, Home_page.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(MainActivity.this, Phone_login.class);
            startActivity(intent);
        }
        finish();
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("Logged", "");
        return savedUsername.equals("yes");
    }
}