package com.example.project_prm.fragment.chats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_prm.R;
import com.example.project_prm.model.Chats;
import com.example.project_prm.util.DateUtil;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;

import java.util.ArrayList;

public class AdapterChats extends RecyclerView.Adapter<AdapterChats.WorldViewHolder> {
    private ArrayList<Chats> chats;
    private Context context;

    private ChatsItemCallBack chatsItemCallBack;

    public AdapterChats(ArrayList<Chats> chats, ChatsItemCallBack chatsItemCallBack, Context context) {
        this.chats = chats;
        this.context = context;
        this.chatsItemCallBack = chatsItemCallBack;
    }

    public void changeDataSet(ArrayList<Chats> newChats) {
        this.chats.clear();
        this.chats.addAll(newChats);
        notifyDataSetChanged();
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
            chatContent = (TextView) view.findViewById(R.id.tvOtherUserChat);
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
    public void onBindViewHolder(@NonNull WorldViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(context);
        String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
        holder.getChatTime().setText(DateUtil.getDateTimeOrTime(chats.get(position).getMessage().getChatTime()));
        if (chats.get(position).getToUserId().equals(uid)) {
            holder.getChatTitle().setText(chats.get(position).getFromUserName());
        } else {
            holder.getChatTitle().setText(chats.get(position).getToUserName());
        }
        holder.getChatContent().setText(chats.get(position).getMessage().getContent());
        Glide.with(context).load(chats.get(position).getAvatar()).circleCrop().into(holder.getImg_chatAva());
        if (!chats.get(position).getMessage().isSeen()) {
            holder.getChatTitle().setTypeface(holder.getChatTitle().getTypeface(), Typeface.BOLD);
            holder.getChatContent().setTypeface(holder.getChatContent().getTypeface(), Typeface.BOLD);
        }
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatsItemCallBack.getChatsItem(chats.get(position));
            }
        });
    }
}

interface ChatsItemCallBack {
    public void getChatsItem(Chats chats);
}
