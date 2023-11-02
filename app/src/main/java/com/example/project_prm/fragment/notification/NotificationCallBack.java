package com.example.project_prm.fragment.notification;

import com.example.project_prm.model.Notification;

import java.util.ArrayList;

public interface NotificationCallBack {
    void onLoading(boolean isLoading);
    void onNotificationResult(boolean result, String message, ArrayList<Notification> notifications);
    void onNotificationChange(ArrayList<Notification> notifications);
}
