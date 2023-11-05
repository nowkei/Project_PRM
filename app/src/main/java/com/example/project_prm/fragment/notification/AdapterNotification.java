package com.example.project_prm.fragment.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_prm.R;
import com.example.project_prm.model.Notification;

import java.util.ArrayList;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.WorldViewHolder> {

    private ArrayList<Notification> notifications;

    private NotificationItemCallBack notificationItemCallBack;

    private Context context;

    public AdapterNotification(ArrayList<Notification> notifications, NotificationItemCallBack notificationItemCallBack, Context context) {
        this.notifications = notifications;
        this.notificationItemCallBack = notificationItemCallBack;
    }

    public void changeDataSet(ArrayList<Notification> notifications) {
        this.notifications.clear();
        this.notifications.addAll(notifications);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class WorldViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgView;
        private TextView tvUsername;
        private TextView tvRq;
        private Button btnAcp;
        private Button btnDecline;

        public WorldViewHolder(View view) {
            super(view);
            imgView = (ImageView) view.findViewById(R.id.img_ava);
            tvUsername = (TextView) view.findViewById(R.id.chat_title);
            tvRq = (TextView) view.findViewById(R.id.chat_content);
            btnAcp = (Button) view.findViewById(R.id.btn_accpect);
            btnDecline = (Button) view.findViewById(R.id.btn_decline);
        }

        public ImageView getImgView() {
            return imgView;
        }

        public TextView getTvUsername() {
            return tvUsername;
        }

        public TextView getTvRq() {
            return tvRq;
        }

        public Button getBtnAcp() {
            return btnAcp;
        }

        public Button getBtnDecline() {
            return btnDecline;
        }
    }
    @NonNull
    @Override
    public AdapterNotification.WorldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.frag_item_notification, parent, false);
        return new WorldViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull WorldViewHolder holder, @SuppressLint("RecyclerView") int position) {
       holder.getTvUsername().setText(notifications.get(position).getUsername());
       holder.getTvRq().setText(notifications.get(position).getContent());
        Glide.with(context).load("").error(R.drawable.userimage).circleCrop().into(holder.getImgView());
       holder.getBtnAcp().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                notificationItemCallBack.onButtonClick("Acp", position);
           }
       });
       holder.getBtnDecline().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                notificationItemCallBack.onButtonClick("Decline", position);
           }
       });
    }
}

interface NotificationItemCallBack {
    public void onButtonClick(String button, int position);
}
