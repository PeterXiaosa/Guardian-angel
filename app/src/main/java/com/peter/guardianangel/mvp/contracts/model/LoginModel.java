package com.peter.guardianangel.mvp.contracts.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.peter.guardianangel.retrofit.BaseResponse;

// Model负责 本地数据的读取，存储，网络，数据库，等等的操作
public class LoginModel extends BaseResponse {
    private String accesstoken = "accesstoken";
    private String account = "account";

    public LoginModel() {
    }

//    public void saveLoginUserInfo() {
//        JsonObject jsonObject = null;
//        if (this.getContent() != null) {
//            jsonObject = new JsonParser().parse(this.getContent().toString()).getAsJsonObject();
//        }
//        if (jsonObject != null) {
//            // Save AccessToken
//            LoginUser.getInstance().setAccessToken(String.valueOf(jsonObject.get(accesstoken)));
//            LoginUser.getInstance().setAccount(String.valueOf(jsonObject.get(account)));
//        }
//    }

//    public void saveLoginUserAccountName(String account){
//        LoginUser.getInstance().setAccount(account);
//    }
}