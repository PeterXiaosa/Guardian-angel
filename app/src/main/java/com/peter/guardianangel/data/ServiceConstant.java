package com.peter.guardianangel.data;

public class ServiceConstant {

    // 长连接建立
    public static final int SERVICE_TYPE_CONNECT_OPEN = 0x1001;
    // 长连接断开
    public static final int SERVICE_TYPE_CONNECT_CLOSE = 0x1002;
    // 长连接断开
    public static final int SERVICE_TYPE_CONNECT_ERROR = 0x1003;
    // 配对成功
    public static final int SERVICE_TYPE_MATCH_SUCCESS = 0x2001;
    // 消息传递（字符串）
    public static final int SERVICE_TYPE_MESSAGE_STRING = 0x3001;
    // 消息传递（字节流）
    public static final int SERVICE_TYPE_MESSAGE_BYTE = 0x3002;
}
