package com.example.project_prm.fragment.chatting;

import com.example.project_prm.model.Message;

import java.util.ArrayList;

public interface ChattingCallback {
    public void onSendMessage(boolean status, String message);
    public void onMessageChange(boolean status,  String message, ArrayList<Message> messages);
}
