package com.example.project_prm.fragment.friends;

import com.example.project_prm.model.Friend;
import com.example.project_prm.model.Info;

import java.util.ArrayList;

public interface FriendsCallBack {
    public void onFriendProfile(boolean status, String message, Info info);
    public void onUserLoginStatusChange(boolean status, String message, String uid, boolean updateStatus);
    public void onFriendResult(boolean status, String result, ArrayList<Friend> u);
    public void onLoading(boolean isLoading);
}
