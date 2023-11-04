package com.example.project_prm.fragment.home;

import com.example.project_prm.model.Chats;
import com.example.project_prm.model.Notification;
import com.example.project_prm.model.User;

import java.util.ArrayList;

public interface HomeCallBack {
    public void onNotificationResult(boolean result, String message, ArrayList<Notification> notifications);

    public void onChatUpdate(boolean result, String message, ArrayList<Chats> Chats);

    public void onUserFriendResult(boolean result, String message, ArrayList<User> user);

    public void onUserProfile(boolean result, String message, User u);

    public void onLoading(boolean isLoading);
}
