package com.peter.guardianangel.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.peter.guardianangel.R;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.UserInfoEditPresenter;
import com.peter.guardianangel.mvp.contracts.view.UserInfoEditView;
import com.peter.guardianangel.util.CalendarUtil;
import com.peter.guardianangel.util.ToastHelper;
import com.peter.guardianangel.view.DateSelectKeyboardDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoEditActivity extends MvpActivity<UserInfoEditPresenter> implements UserInfoEditView {

    @BindView(R.id.activity_userinfo_edit_tv_save)
    TextView tv_save;
    @BindView(R.id.activity_userinfo_edit_ig_back)
    ImageView iv_back;
    @BindView(R.id.activity_user_info_edit_tv_birthday_value)
    TextView tv_birthday;
    @BindView(R.id.activity_user_info_edit_rg_sex)
    RadioGroup rg_sex;
    @BindView(R.id.activity_user_info_edit_rb_female)
    RadioButton rb_female;
    @BindView(R.id.activity_user_info_edit_rb_male)
    RadioButton rb_male;


    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private String mNowDate;

    @Override
    protected void initData() {
        super.initData();

        mNowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tv_birthday.setText(mNowDate);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info_edit;
    }

    @Override
    protected UserInfoEditPresenter createPresenter() {
        return new UserInfoEditPresenter(this);
    }

    @OnClick(R.id.activity_userinfo_edit_ig_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.activity_userinfo_edit_tv_save)
    public void save() {
        boolean ismale = true;
        int buttonid = rg_sex.getCheckedRadioButtonId();
        if (buttonid == R.id.activity_user_info_edit_rb_female) {
            ismale = false;
        }
    }

    @OnClick(R.id.activity_user_info_edit_tv_birthday_value)
    public void birthday() {
        DateSelectKeyboardDialog esDateSelectKeyboardDialog = new DateSelectKeyboardDialog(this, tv_birthday, new DateSelectKeyboardDialog.onItemSelected() {
            @Override
            public void timeSelect(int year, int month, int day) {
                String chooseDateStr = year + "-" + CalendarUtil.dealMonthOrDay(month) + "-" + CalendarUtil.dealMonthOrDay(day);

                try {
                    if (CompareDate(chooseDateStr, mNowDate)) {
                        tv_birthday.setText(chooseDateStr);
                    }else {
                        ToastHelper.show(getApplicationContext(), "您当前选择的日期大于当前日期，请重新选择");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void cancel() {

            }
        });
        esDateSelectKeyboardDialog.show();
    }

    public boolean CompareDate(String formerTime,String laterTime) {
        Date formerDate = null;
        Date laterDate = null;
        try {
            formerDate = format.parse(formerTime);
            laterDate = format.parse(laterTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //  || formerDate.getTime()-laterDate.getTime() ==0
        return formerDate.getTime()-laterDate.getTime() < 0 || formerDate.getTime()-laterDate.getTime() == 0;
    }
}
