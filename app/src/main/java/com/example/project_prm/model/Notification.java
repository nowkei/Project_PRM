package com.example.project_prm.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Notification implements Parcelable {
    private String username;
    private String content;
    private String uid;
    private String avatar;

    public Notification(String username, String content, String uid, String avatar) {
        this.username = username;
        this.content = content;
        this.uid = uid;
        this.avatar = avatar;
    }

    public Notification() {

    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    protected Notification(Parcel in) {
        username = in.readString();
        content = in.readString();
        uid = in.readString();
        avatar = in.readString();
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(content);
        parcel.writeString(uid);
        parcel.writeString(avatar);
    }
}
