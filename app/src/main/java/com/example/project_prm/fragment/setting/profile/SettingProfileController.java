package com.example.project_prm.fragment.setting.profile;

import androidx.annotation.NonNull;

import com.example.project_prm.model.Info;
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
                    Info info = new Info();
                    Map<String,String> data = (HashMap<String, String>) task.getResult().getValue();
                    info.setUid(uid);
                    info.setAddress(data.get("address"));
                    info.setEmail(data.get("email"));
                    info.setPhoneNumber(data.get("phoneNumber"));
                    info.setPassword(data.get("password"));
                    info.setUsername(data.get("userName"));

                    settingProfileCallback.onGetUserResult(true, "Get user profile success", info);
                } else {
                    settingProfileCallback.onGetUserResult(false, "Login fail, please try again", null);
                }
                settingProfileCallback.onLoading(false);
            }
        });
    }
}
