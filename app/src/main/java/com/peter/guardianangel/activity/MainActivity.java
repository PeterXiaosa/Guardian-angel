package com.peter.guardianangel.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.peter.guardianangel.BaseActivity;
import com.peter.guardianangel.R;
import com.peter.guardianangel.annotation.PeterContentViewInject;
import com.peter.guardianangel.annotation.PeterOnClickInject;
import com.peter.guardianangel.annotation.PeterViewInject;

@PeterContentViewInject(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
