package com.example.project_prm.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Chats implements Parcelable {

    String chatTitle;
    String chatContent;
    String chatTime;

    public Chats(String chatTitle, String chatContent, String chatTime) {
        this.chatTitle = chatTitle;
        this.chatContent = chatContent;
        this.chatTime = chatTime;
    }

    protected Chats(Parcel in) {
        chatTitle = in.readString();
        chatContent = in.readString();
        chatTime = in.readString();
    }

    public static final Creator<Chats> CREATOR = new Creator<Chats>() {
        @Override
        public Chats createFromParcel(Parcel in) {
            return new Chats(in);
        }

        @Override
        public Chats[] newArray(int size) {
            return new Chats[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(chatTitle);
        parcel.writeString(chatContent);
        parcel.writeString(chatTime);
    }
}
