package com.example.project_prm.fragment.userprofile;

import com.example.project_prm.model.Chats;

public interface UserProfileCallBack {
    public void onAddFriend(boolean status, String message);

    public void onLoading(boolean isLoading);

    public void onMoveToChat(boolean status, String message, Chats chats);
}
