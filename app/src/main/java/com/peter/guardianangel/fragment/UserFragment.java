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
import com.peter.guardianangel.BaseFragment;
import com.peter.guardianangel.R;
import com.peter.guardianangel.activity.AboutActivity;
import com.peter.guardianangel.activity.EvaluateListActivity;
import com.peter.guardianangel.activity.UserActivity;
import com.peter.guardianangel.activity.UserInfoEditActivity;
import com.peter.guardianangel.bean.MyLocation;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.util.ToastHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class UserFragment extends BaseFragment {

    @BindView(R.id.fragment_user_tv_location)
    TextView tv_location;

    GeoCoder mCoder;

    @BindView(R.id.fragment_user_rl_account)
    RelativeLayout rl_account;
    @BindView(R.id.fragment_user_rl_share)
    RelativeLayout rl_share;
    @BindView(R.id.fragment_user_rl_update)
    RelativeLayout rl_update;
    @BindView(R.id.fragment_user_rl_feedback)
    RelativeLayout rl_feedback;
    @BindView(R.id.fragment_user_rl_about)
    RelativeLayout rl_about;

    @Override
    protected void initData() {
        super.initData();

        initReverseGeoCoder();
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

    @Override
    protected void initView(View root) {
        super.initView(root);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @OnClick(R.id.fragment_user_tv_location)
    public void location() {
        MyLocation myLocation = UserData.getInstance().getPartnerLocation();
        getAddress(myLocation);
    }

    @OnClick(R.id.fragment_user_rl_account)
    public void account() {
        startActivity(new Intent(getActivity(), UserActivity.class));
    }

    @OnClick(R.id.fragment_user_rl_share)
    public void share() {
        ToastHelper.show(getContext(), "功能尚未开放~");
    }

    @OnClick(R.id.fragment_user_rl_update)
    public void update() {
        ToastHelper.show(getContext(), "功能尚未开放~");
    }

    @OnClick(R.id.fragment_user_rl_feedback)
    public void feedback() {
        ToastHelper.show(getContext(), "功能尚未开放~");
    }


    @OnClick(R.id.fragment_user_rl_about)
    public void about() {
        ToastHelper.show(getContext(), "功能尚未开放~");
//        startActivity(new Intent(getActivity(), EvaluateListActivity.class));
    }

    private void getAddress(MyLocation location){
        if (location == null) {
            ToastHelper.show(getContext(), "未获取到地址信息");
            return;
        }
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
