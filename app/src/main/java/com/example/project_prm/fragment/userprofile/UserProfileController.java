package com.example.project_prm.fragment.userprofile;

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

import java.util.HashMap;

public class UserProfileController {

    private UserProfileCallBack userProfileCallBack;

    private DatabaseReference databaseReference;

    public UserProfileController(UserProfileCallBack userProfileCallBack) {
        this.userProfileCallBack = userProfileCallBack;
    }

    public void sendFriendRequest(String currentUid, String currentUserName, String avatar,String sentUserid) {
        userProfileCallBack.onLoading(true);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        // update other user id notification
        HashMap<String, Object> data = new HashMap<>();
        String key = databaseReference.child(sentUserid).child("notifications").push().getKey();
        HashMap<String, String> notificationBody = new HashMap<>();
        notificationBody.put("content", "New Friend Request");
        notificationBody.put("uid", currentUid);
        notificationBody.put("avatar", avatar);
        notificationBody.put("userName", currentUserName);
        data.put(key, notificationBody);
        databaseReference.child(sentUserid).child("notifications").updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //userProfileCallBack.onAddFriend(true, "Send Friend Success");
                } else {
                    //userProfileCallBack.onAddFriend(false, "Send Friend Fail");
                }
            }
        });
        // update other send request
        HashMap<String, Object> requestData = new HashMap<>();
        String sendRequestKey = databaseReference.child(currentUid).child("sendRequests").push().getKey();
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("uid", sentUserid);
        requestData.put(sendRequestKey, requestBody);
        databaseReference.child(currentUid).child("sendRequests").updateChildren(requestData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    userProfileCallBack.onAddFriend(true, "Send Friend Success");
                } else {
                    userProfileCallBack.onAddFriend(false, "Send Friend Fail");
                }
                userProfileCallBack.onLoading(false);
            }
        });
    }

    public void startChat(String currentUserID, String currentUserName, String otherUserID, String otherUserName) {
        userProfileCallBack.onLoading(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("Chats");
        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    boolean isCheck = false;
                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                        HashMap<String, Object> parent = (HashMap<String, Object>) dataSnapshot.getValue();
                        if ((parent.get("toUserID").toString().equalsIgnoreCase(currentUserID)
                                && parent.get("fromUserID").toString().equalsIgnoreCase(otherUserID))
                                || (parent.get("fromUserID").toString().equalsIgnoreCase(currentUserID)
                                && parent.get("toUserID").toString().equalsIgnoreCase(otherUserID))) {
                            String toId = "";
                            if (parent.get("fromUserID").toString().equals(currentUserID)) {
                                toId = parent.get("toUserID").toString();
                            } else {
                                toId = parent.get("fromUserID").toString();
                            }
                            isCheck = true;

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
                                    if (task.isSuccessful()) {
                                        chats.setAvatar(task.getResult().getValue().toString());
                                        chats.setMessage(message);
                                        userProfileCallBack.onMoveToChat(true, "Move To Chat Success" , chats);
                                    } else {
                                        userProfileCallBack.onMoveToChat(false, "Move To Chat Fail", null);
                                    }
                                    userProfileCallBack.onLoading(false);
                                }
                            });
                            return;
                        }
                    }

                    if (!isCheck) {
                        HashMap<String, Object> chatData = new HashMap<>();
                        String key = databaseReference.child("Chats").push().getKey();
                        HashMap<String, Object> chatBody = new HashMap<>();
                        chatBody.put("fromUserID", currentUserID);
                        chatBody.put("fromUserName", currentUserName);
                        chatBody.put("messages", "");
                        chatBody.put("toUserID", otherUserID);
                        chatBody.put("toUserName", otherUserName);
                        chatData.put(key, chatBody);

                        databaseReference.child("Chats").updateChildren(chatData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Chats chats = new Chats();
                                    chats.setChatId(key);
                                    chats.setFromUserId(currentUserID);
                                    chats.setToUserId(otherUserID);
                                    chats.setFromUserName(currentUserName);
                                    chats.setToUserName(currentUserName);
                                    userProfileCallBack.onMoveToChat(false, "Move To Chats Success", chats);
                                } else {
                                    userProfileCallBack.onMoveToChat(false, "Move To Chats Fail", null);
                                }
                                userProfileCallBack.onLoading(false);
                            }
                        });
                    }
                    isCheck = false;
                } else {
                    userProfileCallBack.onMoveToChat(false, "Move To Chat Fail", null);
                    userProfileCallBack.onLoading(false);
                }
            }
        });
    }
}
