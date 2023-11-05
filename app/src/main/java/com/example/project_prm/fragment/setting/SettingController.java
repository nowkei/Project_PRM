package com.example.project_prm.fragment.setting;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SettingController {

    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;

    public SettingController() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void logOut(String uid) {
        firebaseAuth.signOut();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Boolean> hashMap = new HashMap<>();
        hashMap.put("isLogin", false);
        databaseReference.child("Users").child(uid).setValue(hashMap);
    }


}
