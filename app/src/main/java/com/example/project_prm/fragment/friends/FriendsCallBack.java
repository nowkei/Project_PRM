package com.example.project_prm.fragment.friends;

import com.example.project_prm.model.Friend;

import java.util.ArrayList;

public interface FriendsCallBack {
    public void onUserLoginStatusChange(boolean status, String message, String uid, boolean updateStatus);
    public void onFriendResult(boolean status, String result, ArrayList<Friend> u);
    public void onLoading(boolean isLoading);
}
