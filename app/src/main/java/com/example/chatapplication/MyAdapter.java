package com.example.chatapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Java
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Chat_class> data;
    private static OnItemClickListener clickListener;


    public MyAdapter(List<Chat_class> data,OnItemClickListener clickListener)
    {
        this.data = data;
        this.clickListener =clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_people, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat_class item = data.get(position);
        holder.item_Username.setText(item.getUsername());
        holder.item_Message.setText(item.getMessage());
        holder.item_Time.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_Username,item_Message,item_Time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_Username = itemView.findViewById(R.id.Chat_Username);
            item_Message = itemView.findViewById(R.id.Chat_Message);
            item_Time = itemView.findViewById(R.id.Chat_time);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(position); // Pass the position to the listener
                }
            });
        }
    }
}

