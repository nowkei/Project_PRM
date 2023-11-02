package com.example.project_prm.fragment.home;

import android.app.Notification;

import java.util.ArrayList;

public class HomeController {

    private HomeCallBack homeCallBack;

    public HomeController(HomeCallBack homeCallBack) {
        this.homeCallBack = homeCallBack;
    }

    public void getNotification() {
        ArrayList<String> test = new ArrayList<>();
        test.add("hello");
        test.add("Hi");
        test.add("fuck");
        homeCallBack.onNotificationResult(test);
    }

}
