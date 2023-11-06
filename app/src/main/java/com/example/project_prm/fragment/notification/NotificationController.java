package com.example.project_prm.fragment.notification;

import androidx.annotation.NonNull;

import com.example.project_prm.model.Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationController {
    private NotificationCallBack notificationCallBack;

    private DatabaseReference databaseReference;

    public NotificationController(NotificationCallBack notificationCallBack) {
        this.notificationCallBack = notificationCallBack;
    }

    public void removeNotification(int position, ArrayList<Notification> notifications) {
        if (position >= 0 && position < notifications.size()) {
            notifications.remove(position);
            ArrayList<Notification> newList = new ArrayList<>();
            newList.addAll(notifications);
            notificationCallBack.onNotificationChange(newList);
        } else {
            notificationCallBack.onNotificationChange(notifications);
        }
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

    public void declineNotification(String targetUid, String notificationUid) {
        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference targetUserReference = usersReference.child(targetUid);
        targetUserReference.child("notifications").orderByChild("uid").equalTo(notificationUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Xử lý lỗi nếu cần
                    }
                });

        targetUserReference.child("sendRequests").orderByChild("uid").equalTo(notificationUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Xử lý lỗi nếu cần
                    }
                });
    }

}
