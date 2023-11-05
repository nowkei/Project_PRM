package com.example.project_prm.fragment.login;

import com.example.project_prm.model.Info;

public interface ForgetPasswordCallBack {
    void onSendEmailResult(boolean result, String message);

    void onLoading(boolean isLoading);
}
