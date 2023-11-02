package com.example.project_prm.fragment.notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_prm.R;
import com.example.project_prm.model.Notification;

import java.util.ArrayList;

public class ItemClass extends RecyclerView.Adapter<ItemClass.WorldViewHolder> {

    ArrayList<Notification> notifications;

    public ItemClass(ArrayList<Notification> notifications) {
        this.notifications = notifications;
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
            tvUsername = (TextView) view.findViewById(R.id.title);
            tvRq = (TextView) view.findViewById(R.id.content);
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
    public ItemClass.WorldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.frag_item_notification, parent, false);
        return new WorldViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ItemClass.WorldViewHolder holder, int position) {
       holder.getTvUsername().setText(notifications.get(position).getUsername());
       holder.getTvRq().setText(notifications.get(position).getContent());
    }
}
