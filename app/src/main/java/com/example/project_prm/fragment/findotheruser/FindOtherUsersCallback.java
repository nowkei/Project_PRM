package com.example.project_prm.fragment.findotheruser;

import com.example.project_prm.model.Info;

import java.util.ArrayList;

public interface FindOtherUsersCallback {
    public void onFindFriend(boolean status, String message, ArrayList<Info> u);
}
