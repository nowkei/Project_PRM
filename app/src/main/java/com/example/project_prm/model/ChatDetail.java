package com.example.project_prm.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
public class ChatDetail implements Parcelable {
    private String chatId;
    private String toUserId;
    private String fromUserId;
    private ArrayList<Message> messages;
    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    protected ChatDetail(Parcel in) {
        chatId = in.readString();
        toUserId = in.readString();
        fromUserId = in.readString();
        messages = in.createTypedArrayList(Message.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chatId);
        dest.writeString(toUserId);
        dest.writeString(fromUserId);
        dest.writeTypedList(messages);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChatDetail> CREATOR = new Creator<ChatDetail>() {
        @Override
        public ChatDetail createFromParcel(Parcel in) {
            return new ChatDetail(in);
        }

        @Override
        public ChatDetail[] newArray(int size) {
            return new ChatDetail[size];
        }
    };
}
