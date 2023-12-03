package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class forgot_page extends AppCompatActivity {

    Button submit;
    String S_phone;
    EditText E_phone;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_page);

        submit=findViewById(R.id.submit);
        E_phone=findViewById(R.id.E_Phone);


        E_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        E_phone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        E_phone.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
        TextWatcher login=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                S_phone=E_phone.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 10) {
                    s.delete(10, s.length());
                }
            }
        };
        E_phone.addTextChangedListener(login);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                String username=intent.getStringExtra("Username");
                System.out.println(S_phone);
                getUsername(username,S_phone);
            }
        });
    }
    void getUsername(String s_Username,String s_phone)
    {
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    userModel =    task.getResult().toObject(UserModel.class);
                    if(userModel!=null){
                        String t_mobile=userModel.getPhone();
                        //usernameInput.setText(userModel.getUsername());
                        if (s_phone.equals(t_mobile))
                        {
                            Toast.makeText(forgot_page.this, "done", Toast.LENGTH_SHORT).show();
                            /*Intent intent=new Intent(forgot_page.this,Otp_Auth.class);
                            intent.putExtra("U_name",s_Username);
                            startActivity(intent);*/
                        }
                        else
                        {
                            Toast.makeText(forgot_page.this, "Number doesn't match", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(forgot_page.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}