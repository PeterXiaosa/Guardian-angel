package com.peter.guardianangel.activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.peter.guardianangel.BaseActivity;
import com.peter.guardianangel.R;
import com.peter.guardianangel.view.MyToolbar;

import butterknife.BindView;
import butterknife.OnClick;

public class MapActivity extends BaseActivity implements MyToolbar.IToolbarClick{


    @BindView(R.id.activity_map_toolbar)
    MyToolbar toolbar;
    @BindView(R.id.activity_map_mapview)
    MapView mapView;
//    @BindView(R.id.activity_message_ig_back)
//    ImageView iv_back;
//    @BindView(R.id.activity_message_ig_location)
//    ImageView iv_location;

    BaiduMap map;
    LocationClient locationClient;
    boolean isFirstLocation;

    LatLng GEO_SHANGHAI = new LatLng(27.744280, 115.395567);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void initData() {
        locationClient = new LocationClient(this);
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.setCoorType("bd09ll"); // 设置坐标类型
        locationClientOption.setScanSpan(1000);

        //设置locationClientOption
        locationClient.setLocOption(locationClientOption);

        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        locationClient.start();
    }

    @Override
    protected void initView() {
        toolbar.setToolbarClick(this);
        LatLng ll = GEO_SHANGHAI;

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(19.0f);

        map = mapView.getMap();
        map.setMyLocationEnabled(true);

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy((float) 71.25571)
                .direction((float)-1.0).latitude(ll.latitude)
                .longitude(ll.longitude).build();
        map.setMyLocationData(locData);
//        MapStatusUpdate statusUpdate = MapStatusUpdateFactory.newLatLng(GEO_SHANGHAI);
        map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//        map.setMapStatus(statusUpdate);
//        map.animateMapStatus(statusUpdate);
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        locationClient.stop();
        map.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }

    public void setPosition2Center(BaiduMap map, BDLocation bdLocation, Boolean isShowLoc) {
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                .direction(bdLocation.getDirection()).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        map.setMyLocationData(locData);

        if (isShowLoc) {
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(19.0f);
            map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

    @Override
    public void clickIcon(boolean isLeft, int position) {
        if (isLeft && position == 1) {
            finish();
        } else if (!isLeft && position ==1) {
            isFirstLocation = true;
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null){
                return;
            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(location.setDirection()).latitude(location.setLatitude())
//                    .longitude(location.setLongitude()).build();
//            map.setMyLocationData(locData);

            //这个判断是为了防止每次定位都重新设置中心点和marker
            if (isFirstLocation) {
                isFirstLocation = false;
                //设置并显示中心点
                setPosition2Center(map, location, true);
            }
        }
    }
}
