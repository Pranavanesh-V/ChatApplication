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

    public static DocumentReference getChatroomReference(String chatroomId)
    {
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }

    public static String getChatroomId(String userId1,String userId2)
    {
        if (userId1.hashCode()<userId2.hashCode())
        {
            return userId1+"_"+userId2;
        }
        else
        {
            return userId2+"_"+userId1;
        }
    }
    public static CollectionReference getChatroomMessageReference(String chatroomId)
    {
        return getChatroomReference(chatroomId).collection("chats");
    }
}
