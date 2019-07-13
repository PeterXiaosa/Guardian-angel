package com.peter.guardianangel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.peter.guardianangel.activity.MatchCodeActivity;
import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.LoginPresenter;
import com.peter.guardianangel.mvp.contracts.view.LoginView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginView {
    EditText et_account, et_password;
    Button btn_login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        super.initData();

        initPermission();
        UserData.getInstance().init(this);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        et_account = findViewById(R.id.activity_login_et_account);
        et_password = findViewById(R.id.activity_login_et_password);
        btn_login = findViewById(R.id.activity_login_btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void login() {
        String account = et_account.getText().toString();
        String password = et_password.getText().toString();
        final User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setGenkey("testgenkey");
        user.setDeviceId(UserData.getInstance().getDeviceId());

        try {
            Observable.create(new ObservableOnSubscribe<User>() {
                @Override
                public void subscribe(ObservableEmitter<User> e) throws Exception {
                    e.onNext(user);
                }
            }).map(new Function<User, Boolean>() {
                @Override
                public Boolean apply(User user) throws Exception {
                    if (user.getAccount().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Account is null", Toast.LENGTH_SHORT).show();
                        return false;
                    } else if (user.getPassword().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Password is null", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        return true;
                    }
                }
            }).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    presenter.login(user);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "登录成功啦", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MatchCodeActivity.class));
    }

    @Override
    public void loginFail(String errorMsg, int errorCode) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void reflect(String methodName) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int locationFlags = 0;
//        if (requestCode == REQUEST_PERMISSION_LOCATION) {

            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.READ_PHONE_STATE)) {
                    if (grantResults[i] != -1) {
//                        initDeviceID();
                    }
                }
                if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    if (grantResults[i] != -1) {
                        locationFlags += 1;
                    }
                }
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] != -1) {
                        locationFlags += 1;
                    }
                }
            }

            if (locationFlags == 2) {
//                getLocation(true);
            }
//        }
    }

    private void initPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {}
    }
}
