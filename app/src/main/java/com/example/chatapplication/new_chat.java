package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.lang.reflect.Array;
import java.util.Arrays;

public class new_chat extends AppCompatActivity {

    UserModel otherUser;
    ImageButton Back_btn,send_btn;
    TextView Username_chat;
    ImageView Chat_Profile;
    RecyclerView recyclerView;
    TextInputLayout Message;
    EditText E_Message;
    String chatroomId,message="";
    ChatroomModel chatroomModel;
    String savedUsername;
    private static final String PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        Back_btn=findViewById(R.id.Back_btn);
        send_btn=findViewById(R.id.send_btn);
        Username_chat=findViewById(R.id.Username_chat);
        Chat_Profile=findViewById(R.id.Chat_Profile);
        recyclerView=findViewById(R.id.chat_recyclerView);

        Message=findViewById(R.id.Message);
        E_Message=findViewById(R.id.E_Message);
        otherUser=AndroidUtil.getUserModelFromIntent(getIntent());

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        savedUsername = sharedPreferences.getString("username", "");


        chatroomId=FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId(),otherUser.getUserId());

        TextWatcher login=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {

                if (!E_Message.getText().toString().trim().isEmpty())
                {
                    message= E_Message.getText().toString().trim();
                }
            }
        };
        E_Message.addTextChangedListener(login);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.isEmpty())
                {
                    Toast.makeText(new_chat.this, "message", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessageToUser(message);
            }
        });

        Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Username_chat.setText(otherUser.getUsername());

        getOrCreateChatroomModel();


    }

    void sendMessageToUser(String message)
    {
        chatroomModel.setLastMessageTimestamp(Timestamp.now());
        chatroomModel.setLastMessageSenderId(FirebaseUtil.currentUserId());
        FirebaseUtil.getChatroomReference(chatroomId).set(chatroomModel);


        ChatMessageModel chatMessageModel=new ChatMessageModel(message,FirebaseUtil.currentUserId(),Timestamp.now(), savedUsername);
        FirebaseUtil.getChatroomMessageReference(chatroomId).add(chatMessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful())
                        {
                            E_Message.setText("");
                        }
                    }
                });
    }

    void getOrCreateChatroomModel()
    {
        FirebaseUtil.getChatroomReference(chatroomId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    chatroomModel=task.getResult().toObject(ChatroomModel.class);
                    if (chatroomModel==null)
                    {
                        //first time chatting with the person
                        chatroomModel=new ChatroomModel(
                                chatroomId,
                                Arrays.asList(FirebaseUtil.currentUserId(),otherUser.getUserId()),
                                Timestamp.now(),
                                ""
                        );
                        FirebaseUtil.getChatroomReference(chatroomId).set(chatroomModel);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(new_chat.this, Home_page.class);
        startActivity(intent);
        finish();
    }
}