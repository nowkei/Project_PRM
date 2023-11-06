package com.example.project_prm.fragment.findotheruser;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_prm.model.Info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FindOtherUsersController {

    private FindOtherUsersCallback findOtherUsersCallback;

    private DatabaseReference databaseReference;

    public FindOtherUsersController(FindOtherUsersCallback findOtherUsersCallback) {
        this.findOtherUsersCallback = findOtherUsersCallback;
    }

    public void findOtherUser(String containText, String uid, String avatar, String userName) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        ArrayList<Info> infors = new ArrayList<>();
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (containText.isEmpty()) {
                    infors.clear();
                } else {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        HashMap<String , Object> parent = (HashMap<String, Object>) snap.getValue();
                        HashMap<String, String> infoMap = (HashMap<String, String>) parent.get("info");
                        if (((String) infoMap.get("userName")).toLowerCase().contains(containText.toLowerCase()) && !infoMap.get("userId").equalsIgnoreCase(uid)) {
                            Info info = new Info();
                            if (parent.get("notifications") != null) {
                                HashMap<String, HashMap<String, String>> notifications = (HashMap<String, HashMap<String, String>>) parent.get("notifications");
                                HashMap<String, String> checking = new HashMap<>();
                                checking.put("content", "New Friend Request");
                                checking.put("uid", uid);
                                checking.put("avatar", avatar);
                                checking.put("userName", userName);
                                info.setAddFriend(false);
                                if (notifications.containsValue(checking)) {
                                    info.setAddFriend(true);
                                }
                            }
                            info.setUsername(infoMap.get("userName"));
                            info.setEmail(infoMap.get("email"));
                            info.setUid(infoMap.get("userId"));
                            info.setAddress(infoMap.get("address"));
                            info.setAvatar(infoMap.get("avatar"));
                            infors.add(info);
                        }
                    }
                }
                findOtherUsersCallback.onFindFriend(true, "Get Other User Successful", infors);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                findOtherUsersCallback.onFindFriend(false, "Get Other User Fail", infors);
            }
        });
    }
}
