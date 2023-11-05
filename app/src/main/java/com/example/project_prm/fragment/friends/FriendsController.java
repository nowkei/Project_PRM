package com.example.project_prm.fragment.friends;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_prm.model.Friend;
import com.example.project_prm.model.Info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FriendsController {

    private DatabaseReference databaseReference;
    private FriendsCallBack friendsCallBack;
    public FriendsController(FriendsCallBack friendsCallBack) {
        this.friendsCallBack = friendsCallBack;
    }

    public void getFriends(String uid) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        ArrayList<Friend> friends = new ArrayList<Friend>();
        databaseReference.child("Users").child(uid).child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friends.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HashMap<String, String> data = (HashMap<String, String>) dataSnapshot.getValue();
                    Friend friend = new Friend();

                    Info u = new Info();
                    u.setUid(data.get("uid"));
                    u.setUsername(data.get("userName"));
                    u.setAvatar(data.get("avatar"));

                    friend.setOnline(false);
                    friend.setUser(u);

                    friends.add(friend);
                }
                friendsCallBack.onFriendResult(true, "get friend success", friends);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                friendsCallBack.onFriendResult(false, "get friends fail", friends);
            }
        });
    }

    public void checkFriendLogIn(ArrayList<Friend> friends) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        for (Friend fr : friends) {
            databaseReference.child("Users").child(fr.getUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getValue();
                    friendsCallBack.onUserLoginStatusChange(true, "update friend status", fr.getUser().getUid(), (Boolean) data.get("isOnline"));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}
