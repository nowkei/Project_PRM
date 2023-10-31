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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LoginController {
    private LoginCallback loginCallBack;
    private FirebaseAuth firebaseAuth;

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
                    loginCallBack.onLoginResult(true, "Login success");
                } else {
                    loginCallBack.onLoginResult(false, "Login fail, please try again");
                }
                loginCallBack.onLoading(false);
            }
        });
    }
}
