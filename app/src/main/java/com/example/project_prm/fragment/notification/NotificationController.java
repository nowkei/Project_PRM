package com.example.project_prm.fragment.notification;

import com.example.project_prm.model.Notification;

import java.util.ArrayList;

public class NotificationController {
    private NotificationCallBack notificationCallBack;

    public NotificationController(NotificationCallBack notificationCallBack) {
        this.notificationCallBack = notificationCallBack;
    }

    public void getNotification(){
        //notificationCallBack.onLoading(true);
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        Notification no1 = new Notification("Obama", "New friend request");
        Notification no2 = new Notification("Trump", "New friend request");
        Notification no3 = new Notification("Biden", "New friend request");

        notifications.add(no1);
        notifications.add(no2);
        notifications.add(no3);

        notificationCallBack.onNotificationResult(true,"get success", notifications);
        //notificationCallBack.onLoading(false);
    }
}
