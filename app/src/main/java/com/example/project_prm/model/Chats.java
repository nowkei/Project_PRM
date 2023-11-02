package com.example.project_prm.model;

public class Chats {

    String chatTitle;
    String chatContent;
    String chatTime;

    public Chats(String chatTitle, String chatContent, String chatTime) {
        this.chatTitle = chatTitle;
        this.chatContent = chatContent;
        this.chatTime = chatTime;
    }

    public String getChatTitle() {
        return chatTitle;
    }

    public void setChatTitle(String chatTitle) {
        this.chatTitle = chatTitle;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }
    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }
}
