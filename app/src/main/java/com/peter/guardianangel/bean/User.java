package com.peter.guardianangel.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String account;

    private String password;

    private String genkey;

    private String deviceId;

    private String matchCode;

    private String name;

    private int age;

    // 1 is male.
//    private  boolean sex;

    public User() {
    }

    protected User(Parcel in) {
        account = in.readString();
        password = in.readString();
        genkey = in.readString();
        deviceId = in.readString();
        matchCode = in.readString();
        name = in.readString();
        age = in.readInt();
//        sex = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGenkey() {
        return genkey;
    }

    public void setGenkey(String genkey) {
        this.genkey = genkey;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//    public boolean isSex() {
//        return sex;
//    }

//    public void setSex(boolean sex) {
//        this.sex = sex;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.account);
        parcel.writeString(this.password);
        parcel.writeString(this.genkey);
        parcel.writeString(this.deviceId);
        parcel.writeString(this.matchCode);
        parcel.writeString(this.name);
        parcel.writeInt(this.age);
//        parcel.writeBooleanArray(new boolean[]{this.sex});
    }
}
