package com.peter.guardianangel.activity;

import com.peter.guardianangel.R;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.UserPresenter;
import com.peter.guardianangel.mvp.contracts.view.UserView;

public class UserActivity extends MvpActivity<UserPresenter> implements UserView {

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
}
