package com.example.project_prm.fragment.login;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;

import com.example.project_prm.model.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LoginController {

    private User user;
    private LoginCallback loginCallBack;

    public LoginController( LoginCallback loginCallBack ) {
        this.user = new User();
        this.loginCallBack = loginCallBack;
    }

    public void login(String username, String password) {
        loginCallBack.onLoading(true);
        ExecutorService exc = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        exc.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginCallBack.onLoginResult(true, "Success");
                        loginCallBack.onLoading(false);
                    }
                });
            }
        });
    }
}
