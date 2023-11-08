package com.example.project_prm.fragment.friends;

import androidx.annotation.NonNull;

import com.example.project_prm.model.Friend;
import com.example.project_prm.model.Info;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public void getFriendProfile(String friendId) {
        friendsCallBack.onLoading(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(friendId).child("info").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Info info = new Info();
                    Map<String,String> data = (HashMap<String, String>) task.getResult().getValue();
                    info.setUid(friendId);
                    info.setAddress(data.get("address"));
                    info.setAvatar(data.get("avatar"));
                    info.setEmail(data.get("email"));
                    info.setPhoneNumber(data.get("phoneNumber"));
                    info.setUsername(data.get("userName"));
                    info.setFriend(true);
                    friendsCallBack.onFriendProfile(true, "Get user profile success", info);
                } else {
                    friendsCallBack.onFriendProfile(false, "Login fail, please try again", null);
                }
                friendsCallBack.onLoading(false);
            }
        });
    }

}
