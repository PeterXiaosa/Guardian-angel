package com.peter.guardianangel;

import android.app.Activity;
import android.os.Bundle;

import com.peter.guardianangel.annotation.InjectUtils;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);
    }
}
