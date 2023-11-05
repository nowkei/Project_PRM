package com.example.project_prm.fragment.home;

import androidx.annotation.NonNull;

import com.example.project_prm.model.Friend;
import com.example.project_prm.model.Notification;
import com.example.project_prm.model.Info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeController {

    private HomeCallBack homeCallBack;

    private DatabaseReference databaseReference;

    public HomeController(HomeCallBack homeCallBack) {
        this.homeCallBack = homeCallBack;
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
                homeCallBack.onNotificationResult(true,"Get notifications success", notifications);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                homeCallBack.onNotificationResult(false, "Get notifications fail", notifications);
            }
        });
    }

    public void getFriends(String uid) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        ArrayList<Friend> friends = new ArrayList<>();
        databaseReference.child("Users").child(uid).child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friends.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Info u = new Info();
                    Friend fr = new Friend();
                    Map<String,String> data = (HashMap<String, String>) dataSnapshot.getValue();
                    u.setUsername(data.get("userName"));
                    u.setUid(data.get("uid"));
                    u.setAvatar(data.get("avatar"));

                    fr.setUser(u);
                    friends.add(fr);
                }
                homeCallBack.onUserFriendResult(true, "get friend success", friends);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                homeCallBack.onUserFriendResult(true, "get friend fail", friends);
            }
        });
    }

}
