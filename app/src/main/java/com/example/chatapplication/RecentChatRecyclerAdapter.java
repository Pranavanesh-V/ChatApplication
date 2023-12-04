package com.example.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.auth.User;

import org.w3c.dom.Text;

public class RecentChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatroomModel, RecentChatRecyclerAdapter.ChatroomModelViewHolder>
{

    Context context;

    public RecentChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatroomModel> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatroomModelViewHolder holder, int position, @NonNull ChatroomModel model) {

        FirebaseUtil.getOtherUserFromChatroom(model.getUserID())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean lastMessageSentByMe = model.getLastMessageSenderId().equals(FirebaseUtil.currentUserId());
                            UserModel otherUserModel = task.getResult().toObject(UserModel.class);
                            holder.usernameText.setText(otherUserModel.getUsername());
                            if (lastMessageSentByMe) {
                                holder.lastMessageText.setText(String.format("%s%s", context.getString(R.string.you), model.getLastMessage()));
                            } else {
                                holder.lastMessageText.setText(model.getLastMessage());
                            }
                            holder.lastMessageTime.setText(FirebaseUtil.timestampToString(model.getLastMessageTimestamp()));
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(context, new_chat.class);
                                    AndroidUtil.passUserModelAsIntent(intent,otherUserModel );
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            });
                        }
                    }
                });

    }

    @NonNull
    @Override
    public ChatroomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recent_chat_recycler_row,parent,false);
        return new ChatroomModelViewHolder(view);
    }

    class ChatroomModelViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText;
        TextView lastMessageText;
        ImageView profilePic;
        TextView lastMessageTime;

        public ChatroomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText=itemView.findViewById(R.id.user_name_text);
            lastMessageText=itemView.findViewById(R.id.last_message_text);
            profilePic=itemView.findViewById(R.id.profile_pic_image_view);
            lastMessageTime=itemView.findViewById(R.id.last_message_time_text);
        }
    }
}