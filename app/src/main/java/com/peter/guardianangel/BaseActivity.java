package com.peter.guardianangel;

import android.app.Activity;
import android.os.Bundle;

import com.peter.guardianangel.annotation.InjectUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity extends Activity {

    protected abstract int getLayoutId();

    protected void initData(){}

    protected void initView(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ButterKnife.bind(this);

        initData();
        initView();
    }
}
