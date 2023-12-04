package com.example.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class login_page extends AppCompatActivity {
    Button sign_up;
    TextView forgot;
    TextInputLayout Username,Password;
    EditText E_Password,E_user_name;
    String S_Username,S_Password;
    UserModel userModel;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        sign_up=findViewById(R.id.sign_up);
        forgot=findViewById(R.id.forgot);

        Username=findViewById(R.id.Username);
        Password=findViewById(R.id.Password);
        E_user_name=findViewById(R.id.E_user_name);
        E_Password=findViewById(R.id.E_Password);

        E_user_name=Username.getEditText();
        E_Password=Password.getEditText();

        initSharedPreferences();

        TextWatcher login=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                S_Username=E_user_name.getText().toString().trim();
                S_Password=E_Password.getText().toString().trim();

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        E_user_name.addTextChangedListener(login);
        E_Password.addTextChangedListener(login);


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUsername(S_Username,S_Password);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Objects.isNull(S_Username)) {
                    Intent intent = new Intent(login_page.this, forgot_page.class);
                    intent.putExtra("Username", S_Username);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(login_page.this, "Username Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    void getUsername(String s_Username,String s_Password)
    {
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    userModel =    task.getResult().toObject(UserModel.class);
                    if(userModel!=null){
                        String name = userModel.getUsername();
                        String password = userModel.getPassword();
                        System.out.println(s_Username+" "+s_Password);
                        System.out.println(name+password);
                        if (s_Password.equals(password) && s_Username.equals(name))
                        {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Logged", "yes");
                            editor.putString("username",s_Username);
                            editor.apply();

                            Toast.makeText(login_page.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(login_page.this, Home_page.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(login_page.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}