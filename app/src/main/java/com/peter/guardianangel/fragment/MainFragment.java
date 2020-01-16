package com.peter.guardianangel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.peter.guardianangel.BaseFragment;
import com.peter.guardianangel.R;
import com.peter.guardianangel.activity.EvaluateListActivity;
import com.peter.guardianangel.activity.MapActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment extends BaseFragment {
    MapView mapView;
    BaiduMap map;
    LocationClient locationClient;

    TextView tv_detail_location;

    @Override
    protected void initView(View root) {
        super.initView(root);

        tv_detail_location = root.findViewById(R.id.fragment_main_tv_detail_location);
        mapView = root.findViewById(R.id.fragment_main_map);
        map = mapView.getMap();
        map.setMyLocationEnabled(true);

        tv_detail_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MapActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @OnClick(R.id.fragment_main_rl_evaluate)
    public void evaluate() {
        startActivity(new Intent(getActivity(), EvaluateListActivity.class));
    }

    @OnClick(R.id.fragment_main_rl_card)
    public void card() {

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
}
