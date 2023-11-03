package com.example.project_prm.fragment.setting.profile;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_prm.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SettingProfileController {

    private SettingProfileCallback settingProfileCallback;

    private DatabaseReference databaseReferences;

    public SettingProfileController(SettingProfileCallback settingProfileCallback) {
        this.settingProfileCallback = settingProfileCallback;
    }

    public void getUserProfile(String uid) {
        settingProfileCallback.onLoading(true);
        databaseReferences = FirebaseDatabase.getInstance().getReference();
        databaseReferences.child("Users").child(uid).child("info").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    User user = new User();
                    Map<String,String> data = (HashMap<String, String>) task.getResult().getValue();
                    user.setUid(uid);
                    user.setAddress(data.get("address"));
                    user.setEmail(data.get("email"));
                    user.setPhoneNumber(data.get("phoneNumber"));
                    user.setPassword(data.get("password"));
                    user.setUsername(data.get("userName"));

                    settingProfileCallback.onGetUserResult(true, "Get user profile success", user);
                } else {
                    settingProfileCallback.onGetUserResult(false, "Login fail, please try again", null);
                }
                settingProfileCallback.onLoading(false);
            }
        });
    }
}
