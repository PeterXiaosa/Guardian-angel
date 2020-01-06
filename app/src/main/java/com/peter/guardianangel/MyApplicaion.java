package com.peter.guardianangel;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.service.LocationService;
import com.peter.guardianangel.util.CertificateUtil;
import com.peter.guardianangel.util.CrashHandler;
import com.peter.guardianangel.util.DeviceIdentifier;

import org.greenrobot.eventbus.EventBus;

public class MyApplicaion extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);


//        EventBus.builder().addIndex(new MyEventBusIndex())
//                .installDefaultEventBus();
        EventBus.builder().installDefaultEventBus();

        UserData.getInstance().init(this);
        generateGenKey();

        //写崩溃日志
        CrashHandler.getInstance().init(this);
    }

    private void generateGenKey() {
        //初始化生成genKey
        SharedPreferences sharedPreferences = getSharedPreferences("genkeyLibrary", MODE_PRIVATE);
        String genKey = sharedPreferences.getString("genkey", "");

        if (genKey.trim().equals("")) {
            genKey = CertificateUtil.generaterGenKey();
            SharedPreferences sp = getSharedPreferences("genkeyLibrary", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("genkey", genKey);
            editor.apply();
            UserData.getInstance().setGenkey(genKey);
        }else {
            UserData.getInstance().setGenkey(genKey);
        }
    }
}
