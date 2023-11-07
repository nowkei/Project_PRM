package com.example.project_prm.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Chats implements Parcelable {
    private String toUserName;
    private String toUserId;
    private String fromUserName;
    private String fromUserId;
    private String avatar;
    private String chatId;
    private Message message;

    public Chats() {
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    protected Chats(Parcel in) {
        fromUserId = in.readString();
        fromUserId = in.readString();
        toUserId = in.readString();
        toUserName = in.readString();
        chatId = in.readString();
        message = in.readParcelable(Message.class.getClassLoader());
        avatar = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(toUserId);
        parcel.writeString(fromUserId);
        parcel.writeString(fromUserName);
        parcel.writeString(toUserName);
        parcel.writeString(chatId);
        parcel.writeParcelable(message, i);
        parcel.writeString(avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.getChatId().equalsIgnoreCase(((Chats) obj).getChatId());
    }
}
