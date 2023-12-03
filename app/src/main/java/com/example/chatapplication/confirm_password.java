package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class confirm_password extends AppCompatActivity {

    Button submit;
    UserModel userModel;
    String t_Username,t_Mobile,t_Email,username,Password;
    TextInputLayout Confirm_pass,New_pass;
    EditText E_Confirm_pass,E_New_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);

        submit=findViewById(R.id.submit);
        Confirm_pass=findViewById(R.id.Confirm_pass);
        New_pass=findViewById(R.id.New_pass);

        E_Confirm_pass=findViewById(R.id.E_Confirm_pass);
        E_New_pass=findViewById(R.id.E_New_pass);

        username=getIntent().getStringExtra("username22");

        E_New_pass=New_pass.getEditText();
        E_Confirm_pass=Confirm_pass.getEditText();

        TextWatcher login=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {

                if (E_New_pass.getText().toString().trim().equals(E_Confirm_pass.getText().toString().trim()))
                {
                    Password= E_Confirm_pass.getText().toString().trim();
                }
            }
        };
        E_New_pass.addTextChangedListener(login);
        E_Confirm_pass.addTextChangedListener(login);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUsername(username,Password);
            }
        });
    }
    void setUsername(String username,String Password)
    {
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    userModel =    task.getResult().toObject(UserModel.class);
                    if(userModel!=null){
                        t_Username =userModel.getUsername();
                        t_Mobile =userModel.getPhone();
                        t_Email =userModel.getEmail();

                        userModel=new UserModel(t_Mobile,t_Username, Timestamp.now(),t_Email,Password,FirebaseUtil.currentUserId());

                        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Intent intent=new Intent(confirm_password.this, login_page.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(confirm_password.this, "Failed to save", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });


    }
}