package com.example.project_prm.fragment.notification;

import com.example.project_prm.model.Notification;

import java.util.ArrayList;

public interface NotificationCallBack {
    void onLoading(boolean isLoading);
    public void onNotificationResult(boolean result, String message, ArrayList<Notification> notifications);

}
