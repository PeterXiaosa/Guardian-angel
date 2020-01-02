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
import android.view.MenuItem;
import android.widget.TextView;

import com.peter.guardianangel.R;
import com.peter.guardianangel.data.SocketClient;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.fragment.MainFragment;
import com.peter.guardianangel.fragment.RecordFragment;
import com.peter.guardianangel.fragment.UserFragment;
import com.peter.guardianangel.service.LocationService;
import com.peter.guardianangel.util.FragmentHelper;

import java.util.ArrayList;
import java.util.List;

public class ProtectActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private Fragment fragment1, fragment2, fragment3;
    private int lastShowFragment = 3;

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
        SocketClient socketClient = UserData.getInstance().getSocketClient();
        if (socketClient == null) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect);
        navView = findViewById(R.id.nav_view);

        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initFragment();

        initPermission();

        startService();
    }

    private void startService() {
        startService(new Intent(this, LocationService.class));
    }

    private void initFragment() {
        fragment1 = new MainFragment();
        fragment2 = new RecordFragment();
        fragment3 = new UserFragment();
        lastShowFragment = 3;
        FragmentHelper.initFragment(fragment3, this);
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
    protected void onResume() {
        super.onResume();
        if (isJumpActivity()) {
            navView.setSelectedItemId(R.id.navigation_notifications);
        }
    }
}
