package com.example.project_prm.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Friend implements Parcelable {
    private Info u;

    private boolean isOnline;

    public Friend() {
    }

    public Friend(Info u, boolean isOnline) {
        this.u = u;
        this.isOnline = isOnline;
    }

    protected Friend(Parcel in) {
        u = in.readParcelable(Info.class.getClassLoader());
        isOnline = in.readByte() != 0;
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    public Info getUser() {
        return u;
    }

    public void setUser(Info u) {
        this.u = u;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(u, i);
        parcel.writeByte((byte) (isOnline ? 1 : 0));
    }
}
