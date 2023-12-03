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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class register_otp_page extends AppCompatActivity {

    Button submit;
    TextInputLayout Otp;
    String S_opt;
    String phoneNumber;
    TextView resendOtpTextView;
    Long timeoutSeconds = 60L;
    String verificationCode;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    PhoneAuthProvider.ForceResendingToken  resendingToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_otp_page);

        submit=findViewById(R.id.submit_otp);
        Otp=findViewById(R.id.Otp);
        resendOtpTextView =findViewById(R.id.resend_otp_textview);

        phoneNumber=getIntent().getStringExtra("Number");

        sendOtp(phoneNumber,false);


        EditText E_Otp=Otp.getEditText();
        E_Otp.setInputType(InputType.TYPE_CLASS_NUMBER);
        E_Otp.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        E_Otp.setKeyListener(DigitsKeyListener.getInstance("1234567890"));


        //get otp as string from the below
        TextWatcher login=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                S_opt=E_Otp.getText().toString().trim();

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 6) {
                    s.delete(6, s.length());
                }
                if (s.length()==6)
                {
                    submit.setEnabled(true);
                }
                if (s.length()<6)
                {
                    submit.setEnabled(false);
                }
            }
        };
        E_Otp.addTextChangedListener(login);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthCredential credential =  PhoneAuthProvider.getCredential(verificationCode,S_opt);
                signIn(credential);
            }
        });

        resendOtpTextView.setOnClickListener((v)->{
            sendOtp(phoneNumber,true);
        });
    }

    void sendOtp(String phoneNumber,boolean isResend) {
        startResendTimer();
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phoneNumber)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                                Toast.makeText(register_otp_page.this, "verification Successful", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(register_otp_page.this, "OTP verification failed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                Toast.makeText(register_otp_page.this, "Successful", Toast.LENGTH_SHORT).show();
                            }
                        });
        if (isResend) {
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    void signIn(PhoneAuthCredential phoneAuthCredential)
    {
        //login and go to next activity
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Intent intent=new Intent(register_otp_page.this, Authentication_Page.class);
                    intent.putExtra("Phone_num",phoneNumber);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(register_otp_page.this, "Otp verification failed", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    void startResendTimer(){
        resendOtpTextView.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                resendOtpTextView.setText(String.format(getString(R.string.s_d_seconds), getString(R.string.resend_otp_in), timeoutSeconds));
                if(timeoutSeconds<=0){
                    timeoutSeconds =60L;
                    timer.cancel();
                    runOnUiThread(() -> resendOtpTextView.setEnabled(true));
                }
            }
        },0,1000);
    }
}