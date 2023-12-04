package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.auth.User;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
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
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("Logged", "");
        return savedUsername.equals("yes");
    }
}