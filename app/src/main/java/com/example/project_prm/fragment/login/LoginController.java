package com.example.project_prm.fragment.login;

import com.example.project_prm.fragment.login.LoginCallback;
import com.example.project_prm.model.User;

public class LoginController {

    private User user;
    private LoginCallback loginCallBack;

    public LoginController( LoginCallback loginCallBack ) {
        this.user = new User();
        this.loginCallBack = loginCallBack;
    }

    public void login(String username, String password) {
        loginCallBack.onLoginResult(true, "Success");
    }
}
