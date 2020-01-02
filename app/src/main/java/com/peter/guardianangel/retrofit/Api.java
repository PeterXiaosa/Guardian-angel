package com.peter.guardianangel.retrofit;

import com.peter.guardianangel.bean.User;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
    //baseUrl
//    String BASE_URL = "http://106.15.92.137:8080/app/";
//    String IP = "192.168.31.174";
    String IP = "192.168.18.157";
    String BASE_URL = "http://%s:8080/JavaWeb_war_exploded/";

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头

    //登录
    @POST("certificate/login")
    Observable<BaseResponse> login(@Body User userInfo);

    @POST("protect/matchcode/generate")
    Observable<BaseResponse> getMatchCode(@Body RequestBody body);

    @POST("protect/matchcode/auth")
    Observable<BaseResponse> checkMatchCode(@Body RequestBody body);
    // 注册
    @POST("user/register")
    Observable<BaseResponse> register(@Body User user);

    //登录
    @POST("certificate/modifyuserinfo")
    Observable<BaseResponse> updateUserInfo(@Body User userInfo);

    //TODO RequestBody传入服务器的参数不对，服务器无法接受到Json格式，不想创建JavaBean的前提下需要使用RequetBody。
    //TODO 使用@FieldMap需要传入HashMap，同时需要添加注解@FormUrlEncoded。
    //刷新AccessToken
//    @POST("certificate/regettoken")
//    Observable<ServerBean> refreshToken(@Body RequestBody body);

//    // 获取产品信息
//    @POST("product/detail")
//    Observable<MainModel> getProductInfo(@Body RequestBody body);
}
