package com.example.chatapplication;

import android.annotation.SuppressLint;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.List;

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
    public static CollectionReference allChatroomCollectionReference()
    {
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }
    public static DocumentReference getOtherUserFromChatroom(List<String> userId)
    {
        if (userId.get(0).equals(FirebaseUtil.currentUserId()))
        {
            return allUserCollectionReference().document(userId.get(1));
        }
        else
        {
            return allUserCollectionReference().document(userId.get(0));
        }
    }
    @SuppressLint("SimpleDateFormat")
    public static String timestampToString(Timestamp timestamp)
    {
        return new SimpleDateFormat("HH:mm").format(timestamp.toDate());
    }

    public static StorageReference getCurrentProfilePicStorageRef(){
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(FirebaseUtil.currentUserId());
    }

    public static StorageReference  getOtherProfilePicStorageRef(String otherUserId){
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(otherUserId);
    }
}
