package com.example.project_prm.fragment.signup;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.project_prm.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpController {
    private SignUpCallBack signUpCallBack;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferences;

    public SignUpController ( SignUpCallBack signUpCallBack) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.signUpCallBack = signUpCallBack;
    }

    public void signUp(String username, String email, String password) {
        signUpCallBack.onLoading(true);
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    String userId = currentUser.getUid();

                    databaseReferences = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("userId", userId);
                    hashMap.put("password", password);
                    hashMap.put("userName", username);
                    hashMap.put("avatar", "");
                    hashMap.put("address", "");
                    hashMap.put("phoneNumber", "");
                    databaseReferences.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                signUpCallBack.onSignUpResult(true, "Sign up success");
                            } else {
                                signUpCallBack.onSignUpResult(false, "Sign up fail, please try again!");
                            }
                        }
                    });
                } else {
                    signUpCallBack.onSignUpResult(false, "Sign up fail, please try again!");
                }
                signUpCallBack.onLoading(false);
            }
        });
    }
}
