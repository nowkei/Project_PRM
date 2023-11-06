package com.example.project_prm.model;

public class SendRequest {

    private String uid;

    public SendRequest() {
    }

    public SendRequest(String uid) {
        this.uid = uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}
