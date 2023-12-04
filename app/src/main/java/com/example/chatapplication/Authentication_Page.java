package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.hbb20.CountryCodePicker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authentication_Page extends AppCompatActivity {


    Button sign_up;
    TextInputLayout Username,Email,Password;
    EditText E_User_name,E_Email,E_Password;
    String S_Username,S_Email,S_Password,S_Mobile;
    UserModel userModel;
    TextView click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_page);

        sign_up=findViewById(R.id.sign_up);
        click=findViewById(R.id.click);
        Username=findViewById(R.id.Username);
        E_User_name=findViewById(R.id.E_user_name);

        Email=findViewById(R.id.Email);
        E_Email=findViewById(R.id.E_Email);

        Password=findViewById(R.id.Password);
        E_Password=findViewById(R.id.E_Password);



        S_Mobile=getIntent().getStringExtra("Phone_num");

        //Get string values from the TextInputLayout
        E_User_name=Username.getEditText();
        E_Email=Email.getEditText();
        E_Password=Password.getEditText();

        TextWatcher login=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                S_Username=E_User_name.getText().toString().trim();
                S_Email=E_Email.getText().toString().trim();
                S_Password=E_Password.getText().toString().trim();
                Pattern pattern=Pattern.compile("[a-zA-Z0-9]");
                Matcher matcher=pattern.matcher(S_Password);
                boolean isPwdContainSpeChar=matcher.find();
                if (isPwdContainSpeChar)
                {
                    Password.setHelperText("Strong Password");
                    Password.setError("");
                }
                else
                {
                    Password.setHelperText("");
                    Password.setError("Weak Password Include minimum 1 special char");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        E_User_name.addTextChangedListener(login);
        E_Email.addTextChangedListener(login);
        E_Password.addTextChangedListener(login);




        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(S_Username) || TextUtils.isEmpty(S_Mobile) ||TextUtils.isEmpty(S_Email) || TextUtils.isEmpty(S_Password))
                {
                    Toast.makeText(Authentication_Page.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Authentication_Page.this, "Created Successfully", Toast.LENGTH_SHORT).show();
                    System.out.println(S_Username+" "+S_Email+" "+S_Mobile+" "+S_Password);
                    setUsername(S_Username,S_Email,S_Password,S_Mobile);
                }
            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Authentication_Page.this, login_page.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    void setUsername(String username,String Email,String Password,String s_Mobile)
    {
        if (userModel!=null)
        {
            userModel.setUsername(username);
        }
        else
        {
            userModel=new UserModel(s_Mobile,username, Timestamp.now(),Email,Password,FirebaseUtil.currentUserId());
        }
        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Intent intent=new Intent(Authentication_Page.this, login_page.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}