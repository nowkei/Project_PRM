package com.example.project_prm.fragment.chatting;

import androidx.annotation.NonNull;

import com.example.project_prm.model.Chats;
import com.example.project_prm.model.Message;
import com.example.project_prm.util.DateUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class ChattingController {
    private ChattingCallback chattingCallback;
    private DatabaseReference databaseReference;

    public ChattingController(ChattingCallback chattingCallback) {
        this.chattingCallback = chattingCallback;
    }

    public void getMessageList(Chats chat) {
        ArrayList<Message> allChatMessage = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("Chats").child(chat.getChatId()).child("messages");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allChatMessage.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    HashMap<String, Object> parent = (HashMap<String, Object>) data.getValue();
                    Message message = new Message();
                    message.setSendUserId(parent.get("uid").toString());
                    message.setChatTime(parent.get("chatTime").toString());
                    message.setMessageId(data.getKey());
                    message.setContent(parent.get("content").toString());
                    message.setSeen(Boolean.getBoolean(parent.get("isSeen").toString()));
                    allChatMessage.add(message);
                }
                allChatMessage.sort(new Comparator<Message>() {
                    @Override
                    public int compare(Message message, Message t1) {
                        return DateUtil.convertDateFromString(message.getChatTime())
                                .compareTo(DateUtil.convertDateFromString(t1.getChatTime()));
                    }
                });

                chattingCallback.onMessageChange(true, "Get message success", allChatMessage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                chattingCallback.onMessageChange(false, "Get message fail", allChatMessage);
            }
        });
    }

    public void addNewMessage(Chats chat, String content, String uid) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> messageData = new HashMap<>();
        String messageKey = databaseReference.child("Chats")
                .child(chat.getChatId())
                .child("messages").push().getKey();
        HashMap<String, Object> messageBody = new HashMap<>();
        messageBody.put("chatTime", DateUtil.getCurrentDate());
        messageBody.put("content", content);
        messageBody.put("uid", uid);
        messageBody.put("isSeen", false);
        messageData.put(messageKey, messageBody);

        databaseReference.child("Chats").child(chat.getChatId())
                .child("messages").updateChildren(messageData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            HashMap<String, Object> lastMessageBody = new HashMap<>();
                            lastMessageBody.put("chatTime", DateUtil.getCurrentDate());
                            lastMessageBody.put("content", content);
                            lastMessageBody.put("uid", uid);
                            lastMessageBody.put("isSeen", false);
                            lastMessageBody.put("messageId", messageKey);

                            databaseReference.child("Chats").child(chat.getChatId())
                                    .child("lastMessage").setValue(lastMessageBody).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                chattingCallback.onSendMessage(true, "Send Message Success");
                                            } else {
                                                chattingCallback.onSendMessage(false, "Send Message Fail");
                                            }
                                        }
                                    });
                        } else {
                            chattingCallback.onSendMessage(false, "Send Message Fail");
                        }
                    }
                });
    }
}
