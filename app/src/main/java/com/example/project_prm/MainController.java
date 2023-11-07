package com.example.project_prm;

import com.example.project_prm.util.SharedPreferencesUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainController {
    private FirebaseAuth firebaseAuth;
    private static DatabaseReference databaseReferences;


    public MainController() {

    }

    public static void UpLoadImageToFirebase(String uid ,String imageUrl) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("info");

        // Tạo một HashMap để cập nhật chỉ trường avatar
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("avatar", imageUrl);

        // Sử dụng phương thức updateChildren() để cập nhật trường avatar
        databaseReference.updateChildren(updateData)
                .addOnSuccessListener(aVoid -> {

                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi nếu cập nhật thất bại
                });

    }
}
