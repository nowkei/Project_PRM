package com.example.project_prm.fragment.chatting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_prm.R;
import com.example.project_prm.fragment.findotheruser.FindOtherUserAdapter;
import com.example.project_prm.model.ChatDetail;
import com.example.project_prm.model.Chats;
import com.example.project_prm.model.Message;
import com.example.project_prm.util.DateUtil;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Comparator;

public class AdapterChatting extends RecyclerView.Adapter<AdapterChatting.ChattingViewHolder> {
    private Chats chats;
    private ArrayList<Message> messages;
    private Context context;

    public AdapterChatting(ArrayList<Message> messages, Chats chats  ,Context context) {
        this.messages = messages;
        this.context = context;
        this.chats = chats;
    }

    public void changeDataSet(ArrayList<Message> newMessages) {
        this.messages.clear();
        this.messages.addAll(newMessages);
        this.messages.sort(new Comparator<Message>() {
            @Override
            public int compare(Message message, Message t1) {
                return DateUtil.convertDateFromString(message.getChatTime())
                        .compareTo(DateUtil.convertDateFromString(t1.getChatTime()));
            }
        });
        notifyDataSetChanged();
    }

    public static class ChattingViewHolder extends RecyclerView.ViewHolder {

        private TextView tvOtherUserChat;

        private TextView tvCurrentUserChat;

        private ImageView imvOtherUserAvatar;

        private CardView cvOtherAvatar;

        public ChattingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCurrentUserChat = itemView.findViewById(R.id.tvCurrentUserChat);
            tvOtherUserChat = itemView.findViewById(R.id.tvOtherUserChat);
            imvOtherUserAvatar = itemView.findViewById(R.id.imvOtherUserChatAvatar);
            cvOtherAvatar = itemView.findViewById(R.id.cvOtherAvatar);
        }

        public TextView getTvOtherUserChat() {
            return tvOtherUserChat;
        }

        public void setTvOtherUserChat(TextView tvOtherUserChat) {
            this.tvOtherUserChat = tvOtherUserChat;
        }

        public TextView getTvCurrentUserChat() {
            return tvCurrentUserChat;
        }

        public void setTvCurrentUserChat(TextView tvCurrentUserChar) {
            this.tvCurrentUserChat = tvCurrentUserChar;
        }

        public ImageView getImvOtherUserAvatar() {
            return imvOtherUserAvatar;
        }

        public void setImvOtherUserAvatar(ImageView imvOtherUserAvatar) {
            this.imvOtherUserAvatar = imvOtherUserAvatar;
        }

        public CardView getCvOtherAvatar() {
            return cvOtherAvatar;
        }
    }
    @NonNull
    @Override
    public AdapterChatting.ChattingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_chatting, parent, false);
        return new AdapterChatting.ChattingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChatting.ChattingViewHolder holder, int position) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(context);
        String currentUserId = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
        Glide.with(context)
                .load(chats.getAvatar())
                .placeholder(R.drawable.userimage)
                .error(R.drawable.userimage)
                .circleCrop()
                .into(holder.getImvOtherUserAvatar());
        if (messages.get(position).getSendUserId().equalsIgnoreCase(currentUserId)) {
            holder.getCvOtherAvatar().setVisibility(View.GONE);
            holder.getTvCurrentUserChat().setVisibility(View.VISIBLE);
            holder.getTvCurrentUserChat().setText(messages.get(position).getContent());
            holder.getTvOtherUserChat().setVisibility(View.GONE);
        } else {
            holder.getCvOtherAvatar().setVisibility(View.VISIBLE);
            holder.getTvCurrentUserChat().setVisibility(View.GONE);
            holder.getTvOtherUserChat().setText(messages.get(position).getContent());
            holder.getTvOtherUserChat().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
