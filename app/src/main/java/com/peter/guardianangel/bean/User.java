package com.peter.guardianangel.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String account;

    private String password;

    private String genkey;

    private String deviceId;

    private String matchcode;

    private String name;

    private String birthday;

    // 1 is male.
    private  boolean sex;

    public User() {
    }


    protected User(Parcel in) {
        account = in.readString();
        password = in.readString();
        genkey = in.readString();
        deviceId = in.readString();
        matchcode = in.readString();
        name = in.readString();
        birthday = in.readString();
        sex = in.readByte() != 0;
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

    public String getMatchcode() {
        return matchcode;
    }

    public void setMatchcode(String matchcode) {
        this.matchcode = matchcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "account : " + account + ", name : " + name +
                ", birthday : " + birthday + ", sex : " + sex + ", deviceid : " + deviceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(account);
        dest.writeString(password);
        dest.writeString(genkey);
        dest.writeString(deviceId);
        dest.writeString(matchcode);
        dest.writeString(name);
        dest.writeString(birthday);
        dest.writeByte((byte) (sex ? 1 : 0));
    }
}
