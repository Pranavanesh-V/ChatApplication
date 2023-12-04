package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class Phone_login extends AppCompatActivity {

    TextInputLayout Mobile;
    EditText E_Mobile;
    CountryCodePicker countryCodePicker;
    String S_Mobile;
    Button sign_up;
    TextView click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        Mobile=findViewById(R.id.Mobile);
        E_Mobile=findViewById(R.id.E_Mobile);
        countryCodePicker=findViewById(R.id.countryCode);
        sign_up=findViewById(R.id.sign_up);
        click=findViewById(R.id.click);


        //for mobile number
        E_Mobile=Mobile.getEditText();
        assert E_Mobile != null;
        E_Mobile.setInputType(InputType.TYPE_CLASS_NUMBER);
        E_Mobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        E_Mobile.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
        TextWatcher login1=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                S_Mobile=E_Mobile.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 11) {
                    s.delete(10, s.length());
                }
                if (s.length()==10)
                {
                    sign_up.setEnabled(true);
                }
                if (s.length()<10)
                {
                    sign_up.setEnabled(false);
                }
            }
        };
        E_Mobile.addTextChangedListener(login1);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Phone_login.this, register_otp_page.class);
                intent.putExtra("Number",S_Mobile);
                startActivity(intent);
            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Phone_login.this, login_page.class);
                startActivity(intent);
            }
        });
    }
}