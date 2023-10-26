package com.example.project_prm.fragment.signup;

public interface SignUpCallBack {
    void onSignUpResult(boolean result, String message);

    void onLoading(boolean isLoading);
}
