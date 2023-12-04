package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class About_Page extends AppCompatActivity {

    ImageButton back4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        back4=findViewById(R.id.back4);

        back4.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(About_Page.this, Home_page.class);
        startActivity(intent);
        finish();
    }
}