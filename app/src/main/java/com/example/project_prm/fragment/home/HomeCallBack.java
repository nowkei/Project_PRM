package com.example.project_prm.fragment.home;

import com.example.project_prm.model.Chats;
import com.example.project_prm.model.Friend;
import com.example.project_prm.model.Notification;
import com.example.project_prm.model.Info;

import java.util.ArrayList;

public interface HomeCallBack {
    public void onNotificationResult(boolean result, String message, ArrayList<Notification> notifications);

    public void onChatUpdate(boolean result, String message, ArrayList<Chats> Chats);

    public void onUserFriendResult(boolean result, String message, ArrayList<Friend> frs);

    public void onUserProfile(boolean result, String message, Info u);

    public void onLoading(boolean isLoading);
}
