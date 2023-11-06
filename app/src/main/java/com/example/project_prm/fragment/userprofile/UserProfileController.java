package com.example.project_prm.fragment.userprofile;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
}
