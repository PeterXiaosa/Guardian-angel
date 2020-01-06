package com.peter.guardianangel.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.peter.guardianangel.BaseActivity;
import com.peter.guardianangel.R;
import com.peter.guardianangel.util.ToastHelper;
import com.peter.guardianangel.view.starRatingBar.CustomRatingBar;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.activity_about_crb_judge)
    CustomRatingBar customRatingBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {
        super.initData();

//        customRatingBar.setRatingViewClassName("com.peter.guardianangel.view.starRatingBar.MyRatingView1");
        customRatingBar.setOnRatingChangeListener(new CustomRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(float rating, int numStars) {
                ToastHelper.show(getApplicationContext(), "rating : " + rating);
//                Log.e("MainActivity", "rating:" + rating);
            }
        });
    }
}
