package com.example.chatapplication;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Home_page extends AppCompatActivity implements OnItemClickListener {

    ImageView search;
    RecyclerView recyclerView;
    List<Chat_class> itemList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        search = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_view);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home_page.this,Search_Page.class);
                startActivity(intent);
            }
        });


        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(itemList, this);
        recyclerView.setAdapter(adapter);
        Chat_class ch1 = new Chat_class("Pranav", "Your a/c XXXXXXXXXXXX5820 is credited Rs. 70.00 from Mr RISHI ABINANDHAN S on 12/1/2023 8:12:21 PM.info:P2A/370177665954-KVB", "15:58");
        itemList.add(ch1);
        itemList.add(ch1);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    @Override
    public void onItemClick(int position) {
        Chat_class data=itemList.get(position);
        String name=data.getUsername();
        Intent intent=new Intent(Home_page.this,new_chat.class);
        intent.putExtra("Name",name);
        startActivity(intent);
    }

    }