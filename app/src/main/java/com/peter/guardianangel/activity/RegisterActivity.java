package com.peter.guardianangel.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.peter.guardianangel.BaseActivity;
import com.peter.guardianangel.R;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.RegisterPresenter;
import com.peter.guardianangel.mvp.contracts.view.RegisterView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends MvpActivity<RegisterPresenter> implements RegisterView {

    @BindView(R.id.activity_register_et_account)
    EditText et_account;
    @BindView(R.id.activity_register_et_password)
    EditText et_password;
    @BindView(R.id.activity_register_btn_register)
    TextView tv_register;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @OnClick(R.id.activity_register_btn_register)
    public void register() {
        presenter.register(et_account.getText().toString().trim(), et_password.getText().toString().trim());
    }


    @Override
    public void registerSuccess() {
        Toast.makeText(this, "注册成功",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void registerFail(String msg) {
        Toast.makeText(this, "注册失败" + msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }
}
