package com.example.project_prm.fragment.chats;

import com.example.project_prm.model.Chats;

import java.util.ArrayList;

public class ChatsController {
    private ChatsCallBack chatsCallBack;

    public ChatsController(ChatsCallBack chatsCallBack) {
        this.chatsCallBack = chatsCallBack;
    }
    public void getChats(){
        ArrayList<Chats> chats = new ArrayList<Chats>();

        Chats chats1 = new Chats("Obama", "Hey, do you want play soccer with me", "21:49");
        Chats chats2 = new Chats("Biden1231321", "Hey, do you want play soccer with me", "21:59");
        Chats chats3 = new Chats("Butt", "Hey, do you want play soccer with me", "21:49");
        Chats chats4 = new Chats("Thang", "Hey, do you want play soccer with me", "21:39");
        Chats chats5 = new Chats("Cute", "Hey, do you want play soccer with me1233333333333", "21:29");

        chats.add(chats1);
        chats.add(chats2);
        chats.add(chats3);
        chats.add(chats4);
        chats.add(chats5);

        chatsCallBack.onChatsResult(true, "", chats);
    }
}
