package com.peter.guardianangel.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.peter.guardianangel.R;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.data.EventMessage;
import com.peter.guardianangel.data.ServiceConstant;
import com.peter.guardianangel.data.SocketClient;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.fragment.MainFragment;
import com.peter.guardianangel.fragment.RecordFragment;
import com.peter.guardianangel.fragment.UserFragment;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.ProtectPresenter;
import com.peter.guardianangel.mvp.contracts.view.ProtectView;
import com.peter.guardianangel.service.LocationService;
import com.peter.guardianangel.util.DoubleClickExitHelper;
import com.peter.guardianangel.util.FragmentHelper;
import com.peter.guardianangel.util.ToastHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProtectActivity extends MvpActivity<ProtectPresenter> implements ProtectView {

    private Fragment fragment1, fragment2, fragment3;
    private int lastShowFragment = 3;

    protected DoubleClickExitHelper mDoubleClickExitHelper;//双击后退键，退出程序

    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (isJumpActivity()) {
                        startActivity(new Intent(ProtectActivity.this, MatchCodeActivity.class));
                    } else {
                        if (lastShowFragment != 1) {
                            FragmentHelper.switchFragment(fragment1, ProtectActivity.this);
                            lastShowFragment = 1;
                        }
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if (isJumpActivity()) {
                        startActivity(new Intent(ProtectActivity.this, MatchCodeActivity.class));
                    } else {
                        if (lastShowFragment != 2) {
                            FragmentHelper.switchFragment(fragment2, ProtectActivity.this);
                            lastShowFragment = 2;
                        }
                    }
                    return true;
                case R.id.navigation_notifications:
                    if (lastShowFragment != 3) {
                        FragmentHelper.switchFragment(fragment3, ProtectActivity.this);
                        lastShowFragment = 3;
                    }
                    return true;
            }
            return false;
        }
    };

    private boolean isJumpActivity(){
        User user = UserData.getInstance().getUser();

        String partnerAccount = user.getPartnerAccount();
        String loveAuth = user.getLoveAuth();

        if (partnerAccount != null && loveAuth != null && !partnerAccount.isEmpty() && !loveAuth.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_protect;
    }

    @Override
    protected void initData() {
        super.initData();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mDoubleClickExitHelper = new DoubleClickExitHelper(this);

        initFragment();

        initPermission();

        startService();

        longConnect();
    }

    @Override
    protected ProtectPresenter createPresenter() {
        return new ProtectPresenter(this);
    }

    private void longConnect() {
        User userinfo = UserData.getInstance().getUser();

        String partnerAccount = userinfo.getPartnerAccount();
        String loveAuth = userinfo.getLoveAuth();

        if (partnerAccount != null && loveAuth != null && !partnerAccount.isEmpty() && !loveAuth.isEmpty()) {
            SocketClient socketClient = new SocketClient("-1");
            socketClient.connect();
            UserData.getInstance().setSocketClient(socketClient);
        }
    }

    private void startService() {
        startService(new Intent(this, LocationService.class));
    }

    private void initFragment() {
        fragment1 = new MainFragment();
        fragment2 = new RecordFragment();
        fragment3 = new UserFragment();
        lastShowFragment = 3;
        FragmentHelper.initFragment(fragment1, this);
        navView.setSelectedItemId(R.id.navigation_home);
    }

    private void initPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {}
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return mDoubleClickExitHelper.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (isJumpActivity()) {
//            navView.setSelectedItemId(R.id.navigation_notifications);
//        }
        if (!fragment1.isHidden()) {
            navView.setSelectedItemId(R.id.navigation_home);
        } else if (!fragment2.isHidden()) {
            navView.setSelectedItemId(R.id.navigation_dashboard);
        } else if (!fragment3.isHidden()) {
            navView.setSelectedItemId(R.id.navigation_notifications);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, LocationService.class));
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventMessage messageEvent) {
        int action = messageEvent.getAction();
        Object data = messageEvent.getData();
        String content = messageEvent.getContent();
        switch (action) {
            case ServiceConstant.SERVICE_TYPE_MESSAGE_STRING:
                ToastHelper.show(getApplicationContext(), content);
                break;
            case ServiceConstant.SERVICE_TYPE_CONNECT_OPEN:
                connectionOpen();
                break;
            case ServiceConstant.SERVICE_TYPE_MATCH_SUCCESS:
                connectionSuccess();
                break;
            case ServiceConstant.SERVICE_TYPE_CONNECT_CLOSE:
                connectionClose(content);
                break;
            case ServiceConstant.SERVICE_TYPE_CONNECT_ERROR:
                connectError();
            default:
                break;
        }
    }

    private void connectError() {

    }

    @Override
    public void connectionSuccess() {
        // 配对成功之后，主动查询用户信息更新伙伴信息
        User user = UserData.getInstance().getUser();

//        addSubscription(api.queryUserInfo(user), new ApiCallback<BaseResponse>() {
//            @Override
//            public void onSuccess(BaseResponse response, JSONObject responseData) {
//                if (response.isSuccess()) {
//                    User userinfo = JSON.parseObject(responseData.toJSONString(), User.class);
//                    UserData.getInstance().setUser(userinfo);
//                    mvpView.jumpMainPage();
//                }else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(String msg) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });
    }

    @Override
    public void connectionOpen() {
        Log.d("peterfu", "连接建立成功");
//        mvpView.connectionOpen();
    }

    @Override
    public void connectionClose(String reason) {
        Log.d("peterfu", "连接关闭");
        ToastHelper.show(this, "连接关闭 : " + reason);
    }

    @Override
    public void connectionError() {

    }
}
