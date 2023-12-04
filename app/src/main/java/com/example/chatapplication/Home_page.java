package com.example.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class Home_page extends AppCompatActivity  {

    ImageView search,profile;
    RecyclerView recyclerView;
    RecentChatRecyclerAdapter adapter;
    private static final String PREFS_NAME = "MyPrefs";
    ImageButton New_Chat,menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        search = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recycler_view);
        New_Chat =findViewById(R.id.New_Chat);
        menu=findViewById(R.id.menu);
        profile=findViewById(R.id.profile);

        setupRecyclerView();
        getUserData();
        New_Chat.setOnClickListener(view -> {
            Intent intent=new Intent(Home_page.this,Search_Page.class);
            startActivity(intent);
        });
        search.setOnClickListener(view -> {
            Intent intent=new Intent(Home_page.this,Search_Page.class);
            startActivity(intent);
        });
        menu.setOnClickListener(view -> showPopupMenuForMenu());
    }

    void setupRecyclerView()
    {

        Query query= FirebaseUtil.allChatroomCollectionReference()
                .whereArrayContains("userID", FirebaseUtil.currentUserId())
                .orderBy("lastMessageTimestamp",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatroomModel> options=new FirestoreRecyclerOptions.Builder<ChatroomModel>()
                .setQuery(query,ChatroomModel.class).build();

        adapter=new RecentChatRecyclerAdapter(options,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter!=null)
        {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter!=null)
        {
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter!=null)
        {
            adapter.startListening();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void showPopupMenuForMenu() {
        PopupMenu popupMenu = new PopupMenu(this, menu);
        popupMenu.getMenu().add("Profile");
        popupMenu.getMenu().add("Security");
        popupMenu.getMenu().add("About");
        popupMenu.getMenu().add("Logout");


        // Set an item click listener for the PopupMenu
        popupMenu.setOnMenuItemClickListener(item -> {
            // Handle item selection here
            String option=item.getTitle().toString();
            switch (option) {
                case "About": {
                    Intent intent = new Intent(Home_page.this, About_Page.class);
                    startActivity(intent);
                    break;
                }
                case "Logout":
                    logout();
                    break;
                case "Profile": {
                    Intent intent = new Intent(Home_page.this, Profile_page.class);
                    startActivityForResult(intent, 2);
                    break;
                }
                case "Security": {
                    Intent intent = new Intent(Home_page.this, Security_page.class);
                    startActivity(intent);
                    break;
                }
            }

            return true;
        });
        popupMenu.show();
    }

    private void logout() {
        // Clear the saved credentials
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("Logged");
        editor.apply();
        Intent intent=new Intent(Home_page.this, Phone_login.class);
        startActivity(intent);
    }

    void getUserData() {

        FirebaseUtil.getCurrentProfilePicStorageRef().getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        AndroidUtil.setProfilePic(this, uri, profile);
                    }
                });

    }
}