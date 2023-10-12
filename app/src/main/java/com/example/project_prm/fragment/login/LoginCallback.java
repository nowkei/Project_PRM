package com.example.project_prm.fragment.login;

public interface LoginCallback {
    void onLoginResult(boolean result, String message);

    void onSomethingResult();
}
