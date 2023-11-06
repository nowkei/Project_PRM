package com.example.project_prm.fragment.notification;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_prm.model.Notification;
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
import java.util.Locale;
import java.util.Map;

public class NotificationController {
    private NotificationCallBack notificationCallBack;

    private DatabaseReference databaseReference;

    public NotificationController(NotificationCallBack notificationCallBack) {
        this.notificationCallBack = notificationCallBack;
    }

    public void getNotification(String uid){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        databaseReference.child("Users").child(uid).child("notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notifications.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Map<String,String> data = (HashMap<String, String>) dataSnapshot.getValue();
                    Notification notification = new Notification();
                    notification.setAvatar(data.get("avatar"));
                    notification.setUid(data.get("uid"));
                    notification.setContent(data.get("content"));
                    notification.setUsername(data.get("userName"));
                    notifications.add(notification);
                }
                notificationCallBack.onNotificationChange(notifications);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                notificationCallBack.onNotificationChange(notifications);
            }
        });
    }

    public void declineNotification(String currentUserId, Notification notification) {
        notificationCallBack.onLoading(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query notificationsQuery = databaseReference.child("Users").child(currentUserId).child("notifications").orderByChild("uid").equalTo(notification.getUid());
        notificationsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notificationCallBack.onNotificationAcceptOrDecline(false, false, "Decline Friend Request Fail");
            }
        });

        Query requestQuery = databaseReference.child("Users").child(notification.getUid()).child("sendRequests").orderByChild("uid").equalTo(currentUserId);
        requestQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
                notificationCallBack.onLoading(false);
                notificationCallBack.onNotificationAcceptOrDecline(true, false, "Decline Friend Request Success");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                notificationCallBack.onLoading(false);
                notificationCallBack.onNotificationAcceptOrDecline(false, false, "Decline Friend Request Fail");
            }
        });
    }

    public void acceptNotification(String currentUserId, String userName, String avatar, Notification notification) {
        notificationCallBack.onLoading(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query notificationsQuery = databaseReference.child("Users").child(currentUserId).child("notifications").orderByChild("uid").equalTo(notification.getUid());
        notificationsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query requestQuery = databaseReference.child("Users").child(notification.getUid()).child("sendRequests").orderByChild("uid").equalTo(currentUserId);
        requestQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        HashMap<String, Object> data = new HashMap<>();
        String currentUserFriendKey = databaseReference.child("Users").child(currentUserId).child("friends").push().getKey();
        HashMap<String, String> friend = new HashMap<>();
        friend.put("uid", notification.getUid());
        friend.put("userName", notification.getUsername());
        friend.put("avatar", notification.getAvatar());
        data.put(currentUserFriendKey, friend);
        databaseReference.child("Users").child(currentUserId).child("friends").updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    notificationCallBack.onNotificationAcceptOrDecline(true, true, "Accept Friend Request Success");
                } else {
                    notificationCallBack.onNotificationAcceptOrDecline(false, true, "Accept Friend Request Fail");
                    notificationCallBack.onLoading(false);
                }
            }
        });

        HashMap<String, Object> otherData = new HashMap<>();
        String otherUserFriendKey = databaseReference.child("Users").child(notification.getUid()).child("friends").push().getKey();
        HashMap<String, String> newFriend = new HashMap<>();
        newFriend.put("uid", currentUserId);
        newFriend.put("userName", userName);
        newFriend.put("avatar", avatar);
        otherData.put(otherUserFriendKey, newFriend);
        databaseReference.child("Users").child(notification.getUid()).child("friends").updateChildren(otherData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    notificationCallBack.onNotificationAcceptOrDecline(true, true, "Accept Friend Request Success");
                } else {
                    notificationCallBack.onNotificationAcceptOrDecline(false, true, "Accept Friend Request Fail");
                }
                notificationCallBack.onLoading(false);
            }
        });
    }

}
