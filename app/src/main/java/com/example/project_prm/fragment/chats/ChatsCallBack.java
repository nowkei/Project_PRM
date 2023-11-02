package com.example.project_prm.fragment.chats;

import com.example.project_prm.model.Chats;

import java.util.ArrayList;

public interface ChatsCallBack {
    public void onChatsResult(boolean result, String message, ArrayList<Chats> chats);
}
