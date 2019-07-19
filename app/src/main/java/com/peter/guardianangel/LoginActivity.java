package com.peter.guardianangel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.peter.guardianangel.activity.MatchCodeActivity;
import com.peter.guardianangel.activity.ProtectActivity;
import com.peter.guardianangel.activity.UserActivity;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.LoginPresenter;
import com.peter.guardianangel.mvp.contracts.view.LoginView;
import com.peter.guardianangel.util.DeviceIdentifier;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginView, View.OnFocusChangeListener {

    EditText et_account, et_password;
    TextView btn_login;

    @BindView(R.id.activity_login_view_underline_account)
    View underline_account;
    @BindView(R.id.activity_login_view_underline_password)
    View underline_password;

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

        et_account.setOnFocusChangeListener(this);
        et_password.setOnFocusChangeListener(this);

        et_account.setText("fhc");
        et_password.setText("111111");
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.activity_login_btn_login)
    public void login() {
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
                        Toast.makeText(getApplicationContext(), "请输入账户", Toast.LENGTH_SHORT).show();
                        return false;
                    } else if (user.getPassword().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        return true;
                    }
                }
            }).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        presenter.login(user);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void loginSuccess() {
        Toast.makeText(this, "登录成功啦", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(LoginActivity.this, MatchCodeActivity.class));
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
                    if (grantResults[i] != PackageManager.PERMISSION_DENIED) {
                        // Get the Permission
                        // Get the deviceId.
                        DeviceIdentifier.initDeviceId(this);
                    }else {
                        // Forbidden to get permission
                    }
                }
                if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    if (grantResults[i] != PackageManager.PERMISSION_DENIED) {
                        locationFlags += 1;
                    }
                }
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] != PackageManager.PERMISSION_DENIED) {
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
    public void onFocusChange(View view, boolean b) {
        if (view.getId() == R.id.activity_login_et_account) {
            if (b){
                underline_account.setBackgroundColor(getResources().getColor(R.color.login_blue, null));
                underline_password.setBackgroundColor(getResources().getColor(R.color.login_underline, null));
            }
        }else if (view.getId() == R.id.activity_login_et_password) {
            if (b) {
                underline_account.setBackgroundColor(getResources().getColor(R.color.login_underline, null));
                underline_password.setBackgroundColor(getResources().getColor(R.color.login_blue, null));
            }
        }
    }
}
