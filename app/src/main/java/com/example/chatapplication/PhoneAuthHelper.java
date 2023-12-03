package com.example.chatapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuthHelper {
    private static final String TAG = "PhoneAuthHelper";
    private Context context;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;

    public PhoneAuthHelper(Context context) {
        this.context = context;
    }

    public void sendOtp(String phoneNumber, final PhoneAuthCallback callback) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,      // Phone number to verify
                60,               // Timeout duration
                TimeUnit.SECONDS,  // Unit of timeout
                (Activity) context, // Activity (for callback binding)
                getVerificationCallbacks(callback)); // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks getVerificationCallbacks(final PhoneAuthCallback callback) {
        if (verificationCallbacks == null) {
            verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    // This callback will be invoked in two situations:
                    // 1 - Instant verification. In some cases, the phone number can be instantly verified without needing to send or enter an OTP.
                    // 2 - Auto-retrieval. On some devices, Google Play services can automatically detect the incoming verification SMS and perform verification without user action.

                    Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                    callback.onVerificationCompleted(phoneAuthCredential);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    // This callback is invoked if an invalid request for verification is made, for instance, if the phone number format is invalid.
                    Log.w(TAG, "onVerificationFailed", e);
                    callback.onVerificationFailed(e);
                }

                @Override
                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    // The SMS verification code has been sent to the provided phone number.
                    // Save the verification ID and resending token so we can use them later
                    Log.d(TAG, "onCodeSent:" + verificationId);
                    callback.onCodeSent(verificationId, forceResendingToken);
                }
            };
        }
        return verificationCallbacks;
    }

    public interface PhoneAuthCallback {
        void onVerificationCompleted(PhoneAuthCredential credential);

        void onVerificationFailed(Exception e);

        void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token);
    }
}

