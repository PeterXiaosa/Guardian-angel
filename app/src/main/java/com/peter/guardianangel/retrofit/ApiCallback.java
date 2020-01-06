package com.peter.guardianangel.retrofit;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.observers.DisposableObserver;

/**
 * 将RxJava2中的onNext、onError、onComplete的方法封装。
 * @param <T>
 */
public abstract class ApiCallback<T> extends DisposableObserver<T> {

    public abstract void onSuccess(T response, JSONObject responseData);

    public abstract void onFailure(String msg);

    public abstract void onFinish();

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException){
            HttpException httpException = (HttpException) e;
            int errorCode = httpException.code();
            String msg = httpException.getMessage();
            if (errorCode == 504){
                msg = "网络不给力";
            }else if (errorCode == 502 || errorCode == 404){
                msg = "服务器异常，请稍后再试";
            }
            onFailure(msg + errorCode);
        }else {
            onFailure(e.getMessage());
        }
        onFinish();
    }

    @Override
    public void onNext(T value) {
        if (value instanceof BaseResponse && ((BaseResponse) value).isSuccess()) {
            String valueStr = ((BaseResponse) value).data.toString();
//            JsonObject jsonObject = new JsonObject();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", ((BaseResponse) value).msg);
            jsonObject.put("status", ((BaseResponse) value).status);
            if (!valueStr.isEmpty()) {
                jsonObject.put("data", ((BaseResponse) value).data);
            }
            onSuccess(value, jsonObject.getJSONObject("data"));
        }else if (value instanceof BaseResponse){
            onFailure(((BaseResponse) value).msg);
        }else {
            onError(new Throwable("invalid data"));
        }
    }

    @Override
    public void onComplete() {
        onFinish();
    }
}