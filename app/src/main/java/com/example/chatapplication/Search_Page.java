package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.Query;

public class Search_Page extends AppCompatActivity {

    TextInputLayout Search;
    EditText E_Search;
    String S_Search;
    SearchUserRecyclerAdapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        Search=findViewById(R.id.Search);
        E_Search=findViewById(R.id.E_Search);
        recyclerView = findViewById(R.id.recycler_view);

        E_Search=Search.getEditText();

        TextWatcher login=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                S_Search=E_Search.getText().toString().trim();

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        E_Search.addTextChangedListener(login);


        Search.setEndIconOnClickListener(view -> {
            // Handle the click event
            if (S_Search.isEmpty() || S_Search.length()<3)
            {
                Toast.makeText(Search_Page.this, "Invalid Username", Toast.LENGTH_SHORT).show();
            }
            else
            {
                setupSearchRecyclerView(S_Search);
            }
        });
    }
    void setupSearchRecyclerView(String username)
    {

        Query query= FirebaseUtil.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("username",username)
                .whereLessThanOrEqualTo("username",username+'\uf8ff');

        FirestoreRecyclerOptions<UserModel> options=new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query,UserModel.class).build();

        adapter=new SearchUserRecyclerAdapter(options,getApplicationContext());
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
        Intent intent=new Intent(Search_Page.this, Home_page.class);
        startActivity(intent);
        finish();
    }
}