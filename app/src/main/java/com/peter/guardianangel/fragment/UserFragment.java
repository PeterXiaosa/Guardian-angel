package com.peter.guardianangel.fragment;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.peter.guardianangel.R;
import com.peter.guardianangel.activity.UserInfoEditActivity;
import com.peter.guardianangel.bean.MyLocation;
import com.peter.guardianangel.data.UserData;

public class UserFragment extends Fragment {
    TextView tv_location;

    GeoCoder mCoder;

    RelativeLayout rl_account;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_user,container,false);
        initView(root);
        initReverseGeoCoder();
        return root;
    }

    private void initReverseGeoCoder() {
        mCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    return;
                } else {
                    //详细地址
                    String address = reverseGeoCodeResult.getAddress();
                    //行政区号
                    int adCode = reverseGeoCodeResult. getCityCode();
                    Toast.makeText(getContext(), "address : " + address, Toast.LENGTH_SHORT).show();
                }
            }
        };

        mCoder.setOnGetGeoCodeResultListener(listener);
    }

    private void initView(View root) {
        tv_location = root.findViewById(R.id.fragment_user_tv_setting);
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLocation myLocation = UserData.getInstance().getPartnerLocation();
                getAddress(myLocation);
            }
        });

        rl_account = root.findViewById(R.id.fragment_user_rl_account);
        rl_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserInfoEditActivity.class));
            }
        });
    }

    private void getAddress(MyLocation location){
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(latLng)
                        .radius(500));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mCoder.destroy();
    }
}
