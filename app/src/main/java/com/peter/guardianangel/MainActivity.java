package com.peter.guardianangel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.peter.guardianangel.annotation.PeterContentViewInject;
import com.peter.guardianangel.annotation.PeterOnClickInject;
import com.peter.guardianangel.annotation.PeterViewInject;

@PeterContentViewInject(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @PeterViewInject(R.id.tv)
    private TextView tv;
    @PeterViewInject(R.id.btn)
    private Button btn;

    @PeterOnClickInject({R.id.btn})
    public void changText(){
        tv.setText("猴子搬来的救兵 http://blog.csdn.net/mynameishuangshuai");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
