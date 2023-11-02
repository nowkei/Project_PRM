package com.example.project_prm.fragment.login;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import com.example.project_prm.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LoginController {
    private LoginCallback loginCallBack;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReferences;

    public LoginController( LoginCallback loginCallBack ) {
        this.loginCallBack = loginCallBack;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password) {
        loginCallBack.onLoading(true);
        firebaseAuth.signInWithEmailAndPassword( email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = firebaseAuth.getUid();
                    databaseReferences = FirebaseDatabase.getInstance().getReference();
                    databaseReferences.child("Users").child(userId).child("info").child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                loginCallBack.onLoginResult(true, "Login success", task.getResult().getKey(), email, userId);
                            } else {
                                loginCallBack.onLoginResult(false, "Login fail, please try again", "", "", "");
                            }
                        }
                    });
                } else {
                    loginCallBack.onLoginResult(false, "Login fail, please try again", "", "", "" );
                }
                loginCallBack.onLoading(false);
            }
        });
    }
}
