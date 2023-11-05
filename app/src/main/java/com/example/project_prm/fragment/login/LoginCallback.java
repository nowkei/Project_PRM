package com.example.project_prm.fragment.login;

public interface LoginCallback {
    void onLoginResult(boolean result, String message, String username, String email, String userId, String pass);

    void onLoading(boolean isLoading);
}
