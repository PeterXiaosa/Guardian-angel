package com.peter.guardianangel.bean;

public class ServiceInfo {

    // 事件种类。比如登录类，通信类
    private int kind;
    // 事件类型，比如登出，登入事件
    private int type;
    // 事件数据
    private Object data;

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
