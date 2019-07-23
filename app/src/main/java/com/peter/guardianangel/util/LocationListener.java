package com.peter.guardianangel.util;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MyLocationData;

public class LocationListener extends BDAbstractLocationListener {

    public interface Calback{
        void setAccuracy(double accuracy);

        void setDirection(double direction);

        void setLatitude(double latitude);

        void setLongitude(double longitude);
    }

    private Calback calback;

    public LocationListener(Calback calback) {
        this.calback = calback;
    }

    @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            float accuracy = location.getRadius();
            float direction = location.getDirection();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            calback.setAccuracy(accuracy);
            calback.setDirection(direction);
            calback.setLatitude(latitude);
            calback.setLongitude(longitude);
        }
    }
