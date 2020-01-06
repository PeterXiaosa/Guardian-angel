package com.peter.guardianangel.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.peter.guardianangel.R;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.UserPresenter;
import com.peter.guardianangel.mvp.contracts.view.UserView;

import butterknife.BindView;
import butterknife.OnClick;

public class UserActivity extends MvpActivity<UserPresenter> implements UserView {

    @BindView(R.id.activity_user_tv_name_value)
    TextView tv_name;
    @BindView(R.id.activity_user_tv_sex_value)
    TextView tv_sex;
    @BindView(R.id.activity_user_tv_birthday_value)
    TextView tv_birthday;
    @BindView(R.id.activity_user_tv_edit)
    TextView tv_edit;
    @BindView(R.id.activity_user_ig_back)
    ImageView iv_back;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getUserInfo();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter(this);
    }

    @Override
    public void updateUserInfo(User user) {
        tv_name.setText(user.getName());
        tv_birthday.setText(user.getBirthday());
        tv_sex.setText(user.getSex() ? "男" : "女");
    }

    @Override
    public void updateUserInfoFail() {

    }

    @OnClick(R.id.activity_user_ig_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.activity_user_tv_edit)
    public void edit() {
        startActivity(new Intent(UserActivity.this, UserInfoEditActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.getUserInfo();
    }
}
