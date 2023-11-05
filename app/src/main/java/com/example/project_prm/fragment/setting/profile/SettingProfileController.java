package com.example.project_prm.fragment.setting.profile;

import androidx.annotation.NonNull;

import com.example.project_prm.model.Info;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SettingProfileController {
    private SettingProfileCallback settingProfileCallback;

    private DatabaseReference databaseReferences;
    private FirebaseAuth firebaseAuth;

    public SettingProfileController(SettingProfileCallback settingProfileCallback) {
        this.settingProfileCallback = settingProfileCallback;
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public  void updateData(String uid,String username,String userEmail, String adderss, String newPassword){
        settingProfileCallback.onLoading(true);
        if(!newPassword.isEmpty()){
            firebaseAuth.getCurrentUser().updatePassword(newPassword);
        }
        String userId = uid;
        databaseReferences = FirebaseDatabase.getInstance().getReference();
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("address", adderss);
        hashMap.put("userName", username);
        hashMap.put("email", userEmail);
        hashMap.put("avatar", "");
        hashMap.put("phoneNumber", "");
        hashMap.put("userId", userId);
        databaseReferences.child("Users").child(userId).child("info").setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    settingProfileCallback.onUpdateResult(true, "Update success");
                } else {
                    settingProfileCallback.onUpdateResult(false, "Update fail, please try again!");
                }
                settingProfileCallback.onLoading(false);
            }
        });
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
