package com.peter.guardianangel.retrofit;

public class BaseResponse<T> {
    public int status;

    public String msg;

    public T data;

    public boolean isSuccess() {
        //TODO 后期服务器建议修改为200
        return status == 0;
    }
}