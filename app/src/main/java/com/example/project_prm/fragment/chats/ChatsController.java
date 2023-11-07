package com.example.project_prm.fragment.chats;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_prm.model.Chats;
import com.example.project_prm.model.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ChatsController {

    private int count = 0;
    private ChatsCallBack chatsCallBack;
    private DatabaseReference databaseReference;
    public ChatsController(ChatsCallBack chatsCallBack) {
        this.chatsCallBack = chatsCallBack;
    }

    public void getChats(String uid) {
        chatsCallBack.onLoading(true);
        ArrayList<Chats> allChat = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("Chats");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allChat.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    HashMap<String, Object> parent = (HashMap<String, Object>) dataSnapshot.getValue();
                    count++;
                    if (parent.get("toUserID").toString().equalsIgnoreCase(uid) || parent.get("fromUserID").toString().equalsIgnoreCase(uid)) {
                        String toId = "";
                        if (parent.get("fromUserID").toString().equals(uid)) {
                            toId = parent.get("toUserID").toString();
                        } else {
                            toId = parent.get("fromUserID").toString();
                        }

                        Chats chats = new Chats();
                        chats.setChatId(dataSnapshot.getKey());

                        chats.setFromUserId(parent.get("fromUserID").toString());
                        chats.setToUserId(parent.get("toUserID").toString());

                        chats.setFromUserName(parent.get("fromUserName").toString());
                        chats.setToUserName(parent.get("toUserName").toString());

                        Message message = new Message();
                        if (parent.get("lastMessage") != null) {
                            HashMap<String, Object> lastMessage = (HashMap<String, Object>) parent.get("lastMessage");
                            message.setChatTime(lastMessage.get("chatTime").toString());
                            message.setMessageId(lastMessage.get("messageId").toString());
                            message.setContent(lastMessage.get("content").toString());
                            message.setSeen((Boolean) lastMessage.get("isSeen"));
                            message.setSendUserId(lastMessage.get("uid").toString());
                        } else {
                            message.setChatTime("");
                            message.setMessageId("");
                            message.setContent("");
                            message.setSeen(true);
                            message.setSendUserId("");
                        }
                        databaseReference.child("Users").child(toId).child("info").child("avatar").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                chats.setAvatar(task.getResult().getValue().toString());
                                chats.setMessage(message);
                                if (!message.isSeen()) {
                                    allChat.add(0, chats);
                                } else {
                                    allChat.add(chats);
                                }
                                chatsCallBack.onChatUpdate(chats);
                                chatsCallBack.onChatsResult(true, "Get All Chat Success", allChat);
                            }
                        });
                    }
                }
                allChat.clear();
                chatsCallBack.onLoading(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                allChat.clear();
                chatsCallBack.onLoading(false);
                chatsCallBack.onChatsResult(false, "Get All Chat Fail", allChat);
            }
        });
    }
}
