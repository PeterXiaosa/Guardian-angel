package com.peter.guardianangel.retrofit;

import com.example.utillib.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final int TIME_OUT = 4;

    private static Retrofit mRetrofit;

    public static Retrofit retrofit(){
        if(mRetrofit == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            // TODO 通过添加Interceptor来给URL添加公共参数

            if (BuildConfig.DEBUG){
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log模式
                builder.addInterceptor(loggingInterceptor);
            }
            OkHttpClient okHttpClient = builder.build();
            okHttpClient.newBuilder().connectTimeout(TIME_OUT, TimeUnit.SECONDS);
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(String.format(Api.BASE_URL, Api.IP))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }
}
