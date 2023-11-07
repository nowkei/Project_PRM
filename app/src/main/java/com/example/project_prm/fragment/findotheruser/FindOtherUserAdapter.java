package com.example.project_prm.fragment.findotheruser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_prm.R;
import com.example.project_prm.model.Info;

import java.util.ArrayList;

public class FindOtherUserAdapter extends RecyclerView.Adapter<FindOtherUserAdapter.FindOtherUserViewHolder> {

    private ArrayList<Info> infos;

    private Context context;

    private FindOtherUserItemCallBack findOtherUsersCallback;

    public FindOtherUserAdapter(ArrayList<Info> infos, FindOtherUserItemCallBack findOtherUsersCallback, Context context) {
        this.infos = infos;
        this.context = context;
        this.findOtherUsersCallback = findOtherUsersCallback;
    }

    public void changeDataSet(ArrayList<Info> infos) {
        this.infos.clear();
        this.infos.addAll(infos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FindOtherUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend, parent, false);
        return new FindOtherUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindOtherUserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getTvUserName().setText(infos.get(position).getUsername());
        holder.getTvStatus().setVisibility(View.INVISIBLE);
        Glide.with(context).load(infos.get(position).getAvatar()).error(R.drawable.userimage).circleCrop().into(holder.getImvAvatar());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findOtherUsersCallback.onItemClick(infos.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public static class FindOtherUserViewHolder extends RecyclerView.ViewHolder {

        private ImageView imvAvatar;

        private TextView tvUserName;

        private TextView tvStatus;

        private View view;
        public FindOtherUserViewHolder(View itemView) {
            super(itemView);
            imvAvatar = itemView.findViewById(R.id.imvFriendAvatar);
            tvUserName = itemView.findViewById(R.id.tvFriendUsername);
            tvStatus = itemView.findViewById(R.id.tvStatus);
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

        public View getView() {
            return view;
        }
    }
}

interface FindOtherUserItemCallBack {
    public void onItemClick(Info user);
}
