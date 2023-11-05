package com.example.project_prm.fragment.setting.profile;

import com.example.project_prm.model.Info;

public interface SettingProfileCallback {

    void onGetUserResult(boolean result, String message, Info u);

    void onLoading(boolean isLoading);

}
