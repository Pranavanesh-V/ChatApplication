package com.example.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

public class new_chat extends AppCompatActivity {

    UserModel otherUser;
    ImageButton Back_btn,send_btn;
    TextView Username_chat;
    ImageView Chat_Profile;
    RecyclerView recyclerView;
    ChatRecyclerAdapter adapter;
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


        E_Message=findViewById(R.id.E_Message);
        otherUser=AndroidUtil.getUserModelFromIntent(getIntent());

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        savedUsername = sharedPreferences.getString("username", "");


        chatroomId=FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId(),otherUser.getUserId());

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message=E_Message.getText().toString().trim();
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
        setupChatRecyclerView();


    }

    void setupChatRecyclerView()
    {
        Query query = FirebaseUtil.getChatroomMessageReference(chatroomId)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query,ChatMessageModel.class).build();

        adapter = new ChatRecyclerAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
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