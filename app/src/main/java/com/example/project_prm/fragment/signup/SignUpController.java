package com.example.project_prm.fragment.signup;

import android.os.Looper;

import com.example.project_prm.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class SignUpController {
    private User user;
    private SignUpCallBack signUpCallBack;

    public SignUpController ( SignUpCallBack signUpCallBack){
        this.user = new User();
        this.signUpCallBack = signUpCallBack;
    }

    public void signUp(String username, String email, String password){
        signUpCallBack.onLoading(true);
        ExecutorService exc = Executors.newSingleThreadExecutor();

    }
}
