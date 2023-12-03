package com.example.chatapplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtil
{
    public static String currentUserId()
    {
        return FirebaseAuth.getInstance().getUid();
    }
    public static DocumentReference currentUserDetails()
    {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }
    public static CollectionReference allUserCollectionReference()
    {
        return FirebaseFirestore.getInstance().collection("users");
    }
}
