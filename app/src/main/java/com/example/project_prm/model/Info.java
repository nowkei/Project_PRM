package com.example.project_prm.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Info implements Parcelable {

    private String uid;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String address;

    protected Info(Parcel in) {
        uid = in.readString();
        username = in.readString();
        password = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        avatar = in.readString();
        address = in.readString();
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Info() {

    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Info(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(email);
        parcel.writeString(phoneNumber);
        parcel.writeString(avatar);
        parcel.writeString(address);
    }
}
