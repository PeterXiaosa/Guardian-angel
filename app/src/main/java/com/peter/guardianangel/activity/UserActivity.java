package com.peter.guardianangel.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.peter.guardianangel.R;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.UserPresenter;
import com.peter.guardianangel.mvp.contracts.view.UserView;
import com.peter.guardianangel.view.MyToolbar;

import butterknife.BindView;
import butterknife.OnClick;

public class UserActivity extends MvpActivity<UserPresenter> implements UserView , MyToolbar.IToolbarClick{

    @BindView(R.id.activity_user_tv_name_value)
    TextView tv_name;
    @BindView(R.id.activity_user_tv_sex_value)
    TextView tv_sex;
    @BindView(R.id.activity_user_tv_birthday_value)
    TextView tv_birthday;
    @BindView(R.id.activity_user_toolbar)
    MyToolbar toolbar;

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
    protected void initView() {
        super.initView();
        toolbar.setToolbarClick(this);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.getUserInfo();
    }

    @Override
    public void clickIcon(boolean isLeft, int position) {
        if (isLeft && position == 1) {
            finish();
        } else if (!isLeft && position == 1) {
            startActivity(new Intent(UserActivity.this, UserInfoEditActivity.class));
        }
    }
}
