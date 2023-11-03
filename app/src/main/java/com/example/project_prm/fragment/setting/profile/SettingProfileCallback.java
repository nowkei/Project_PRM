package com.example.project_prm.fragment.setting.profile;

import com.example.project_prm.model.User;

public interface SettingProfileCallback {

    void onGetUserResult(boolean result, String message, User u);

    void onLoading(boolean isLoading);

}
