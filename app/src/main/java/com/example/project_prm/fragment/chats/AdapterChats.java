package com.example.project_prm.fragment.chats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_prm.R;
import com.example.project_prm.model.Chats;

import java.util.ArrayList;

public class AdapterChats extends RecyclerView.Adapter<AdapterChats.WorldViewHolder> {

    ArrayList<Chats> chats;

    public AdapterChats(ArrayList<Chats> chats) {
        this.chats = chats;
    }


    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class WorldViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_chatAva;
        private TextView chatTitle;
        private TextView chatContent;
        private TextView chatTime;
        private  View view;
        public WorldViewHolder(@NonNull View view) {
            super(view);
            img_chatAva = (ImageView) view.findViewById(R.id.img_chatAvatar);
            chatTitle = (TextView) view.findViewById(R.id.chat_title);
            chatContent = (TextView) view.findViewById(R.id.chat_content);
            chatTime = (TextView) view.findViewById(R.id.chat_time);
            this.view = view;
        }

        public ImageView getImg_chatAva() {
            return img_chatAva;
        }

        public TextView getChatTitle() {
            return chatTitle;
        }

        public View getView() {
            return view;
        }

        public TextView getChatContent() {
            return chatContent;
        }

        public TextView getChatTime() {
            return chatTime;
        }
    }
    @NonNull
    @Override
    public AdapterChats.WorldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_chats, parent, false);
        return new WorldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorldViewHolder holder, int position) {
        holder.getChatContent().setText(chats.get(position).getChatContent());
        holder.getChatTitle().setText(chats.get(position).getChatTitle());
        holder.getChatTime().setText(chats.get(position).getChatTime());


    }
}
