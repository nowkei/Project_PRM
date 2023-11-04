package com.example.project_prm.fragment.notification;

import com.example.project_prm.model.Notification;

import java.util.ArrayList;

public class NotificationController {
    private NotificationCallBack notificationCallBack;

    public NotificationController(NotificationCallBack notificationCallBack) {
        this.notificationCallBack = notificationCallBack;
    }

    public void removeNotification(int position, ArrayList<Notification> notifications) {
        if (position >= 0 && position < notifications.size()) {
            notifications.remove(position);
            ArrayList<Notification> newList = new ArrayList<>();
            newList.addAll(notifications);
            notificationCallBack.onNotificationChange(newList);
        } else {
            //notificationCallBack.onNotificationResult(false, "Invalid position", notifications);
        }
    }

}
