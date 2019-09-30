package com.peter.guardianangel.bean;

import android.os.Parcel;

import java.io.Serializable;

public class SocketInfo implements Serializable {
    private int eventType;

    private int errorCode;

    private Object data;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
