package com.peter.guardianangel.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.peter.guardianangel.bean.MyLocation;
import com.peter.guardianangel.data.WebSocketConnect;
import com.peter.guardianangel.util.LocationListener;
import com.peter.guardianangel.util.SerializeUtil;

import org.java_websocket.client.WebSocketClient;

import java.util.Timer;
import java.util.TimerTask;

public class LocationService extends Service implements LocationListener.Calback{
    
    private String TAG = "LocationService";

    LocationClient locationClient;

    WebSocketClient webSocketClient;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        initLocationClient();

        super.onCreate();
    }

    private void initLocationClient() {
        locationClient = new LocationClient(this);
        //设置locationClientOption
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setCoorType("bd09ll"); // 设置坐标类型
        locationClientOption.setScanSpan(1000);
        //设置是否需要地址信息，默认为无地址
        locationClientOption.setIsNeedAddress(true);
        //设置是否打开gps进行定位
        locationClientOption.setOpenGps(true);
        //设置精度
        locationClientOption.setLocationMode(com.baidu.location.LocationClientOption.LocationMode.Hight_Accuracy);

        locationClient.setLocOption(locationClientOption);
        LocationListener locationListener = new LocationListener(this);
        locationClient.registerLocationListener(locationListener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!locationClient.isStarted()){
                    locationClient.start();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0 ,1000 * 50);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationClient.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void setAccuracy(double accuracy) {
//        Log.d(TAG, "setAccuracy: " + accuracy);
    }

    @Override
    public void setDirection(double direction) {
//        Log.d(TAG, "setDirection: " + direction);
    }

    @Override
    public void setLatitude(double latitude) {

//        Log.d(TAG, "setLatitude: " + latitude);
    }

    @Override
    public void setLongitude(double longitude) {

    }

    @Override
    public void setLocation(double accuracy, double direction, double latitude, double longitude) {
        if (webSocketClient == null) {
            webSocketClient = WebSocketConnect.getInstance().getWebSocketClient();
            if (webSocketClient != null) {
                sendLocation(accuracy, direction, latitude, longitude);
            }
        }else {
            sendLocation(accuracy, direction, latitude, longitude);
        }
    }

    private void sendLocation(double accuracy, double direction, double latitude, double longitude){
        try {
            MyLocation myLocation = MyLocation.class.newInstance();
            myLocation.setAccuracy(accuracy);
            myLocation.setDirection(direction);
            myLocation.setLatitude(latitude);
            myLocation.setLongitude(longitude);
            webSocketClient.send(SerializeUtil.serialize(myLocation));
            Log.d(TAG, "setLatitude: " + latitude + ", setLongitude: " + longitude);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
