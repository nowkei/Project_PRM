package com.example.project_prm.fragment.friends.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_prm.R;
import com.example.project_prm.model.Friend;

import java.util.ArrayList;

public class FriendItemAdapter extends RecyclerView.Adapter<FriendItemAdapter.FriendItemViewHolder> {

    ArrayList<Friend> friends;

    Context context;

    FriendItemCallBack friendItemCallBack;

    public FriendItemAdapter(ArrayList<Friend> users, FriendItemCallBack friendItemCallBack, Context context) {
        this.friendItemCallBack = friendItemCallBack;
        this.friends = users;
        this.context = context;
    }

    public void changeDataSet(ArrayList<Friend> newUsers) {
        this.friends.clear();
        this.friends.addAll(newUsers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend, parent, false);
        return new FriendItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getTvUserName().setText(friends.get(position).getUser().getUsername());
        Glide.with(context).load(friends.get(position).getUser().getAvatar()).placeholder(R.drawable.userimage).error(R.drawable.userimage).circleCrop().into(holder.getImvAvatar());
        if (friends.get(position).isOnline()) {
            holder.getTvStatus().setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.green, null));
            holder.getTvStatus().setText(R.string.online);
        } else {
            holder.getTvStatus().setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.red, null));
            holder.getTvStatus().setText(R.string.offline);
        }
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendItemCallBack.onFriendProfile(friends.get(position).getUser().getUid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public static class FriendItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUserName;

        private ImageView imvAvatar;

        private TextView tvStatus;

        private View view;

        public FriendItemViewHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tvFriendUsername);
            imvAvatar = (ImageView) itemView.findViewById(R.id.imvFriendAvatar);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            view = itemView;
        }

        public TextView getTvUserName() {
            return tvUserName;
        }

        public ImageView getImvAvatar() {
            return imvAvatar;
        }

        public TextView getTvStatus() {
            return tvStatus;
        }

        public View getView() { return view; }
    }
}
