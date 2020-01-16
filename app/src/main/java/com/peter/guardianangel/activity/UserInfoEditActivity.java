package com.peter.guardianangel.activity;

import android.annotation.SuppressLint;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.peter.guardianangel.R;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.UserInfoEditPresenter;
import com.peter.guardianangel.mvp.contracts.view.UserInfoEditView;
import com.peter.guardianangel.util.CalendarUtil;
import com.peter.guardianangel.util.ToastHelper;
import com.peter.guardianangel.view.MyToolbar;
import com.peter.guardianangel.view.daypicker.DateSelectKeyboardDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class UserInfoEditActivity extends MvpActivity<UserInfoEditPresenter> implements UserInfoEditView , MyToolbar.IToolbarClick{

    @BindView(R.id.activity_user_info_edit_tv_birthday_value)
    TextView tv_birthday;
    @BindView(R.id.activity_user_info_edit_rg_sex)
    RadioGroup rg_sex;
    @BindView(R.id.activity_user_info_edit_rb_female)
    RadioButton rb_female;
    @BindView(R.id.activity_user_info_edit_rb_male)
    RadioButton rb_male;
    @BindView(R.id.activity_user_info_edit_et_name)
    EditText et_name;
    @BindView(R.id.activity_user_info_edit_toolbar)
    MyToolbar toolbar;

    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private String mNowDate;

    @Override
    protected void initData() {
        super.initData();

        mNowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tv_birthday.setText(mNowDate);
    }

    @Override
    protected void initView() {
        super.initView();

        toolbar.setToolbarClick(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info_edit;
    }

    @Override
    protected UserInfoEditPresenter createPresenter() {
        return new UserInfoEditPresenter(this);
    }

    @SuppressLint("CheckResult")
    private void save() {
        boolean ismale = true;
        int buttonid = rg_sex.getCheckedRadioButtonId();
        if (buttonid == R.id.activity_user_info_edit_rb_female) {
            ismale = false;
        }
        final User user = UserData.getInstance().getUser();
        user.setBirthday(tv_birthday.getText().toString());
        user.setName(et_name.getText().toString());
        user.setSex(ismale);
        
        Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(ObservableEmitter<User> e) throws Exception {
                e.onNext(user);
            }
        }).map(new Function<User, Boolean>()
        {
            @Override
            public Boolean apply(User user) throws Exception {
                if (user.getName() == null || user.getName().isEmpty()) {
                    return false;
                }
                if (user.getBirthday() == null || user.getName().isEmpty()) {
                    return false;
                }
                return true;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    presenter.updateUserInfo(user);
                } else {
                    ToastHelper.show(getApplicationContext(), "请填写完善信息");
                }
            }
        });
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

    @Override
    public void updateUserInfoSuccessfully() {
        ToastHelper.show(this, "用户信息更新成功");
        finish();
    }

    @Override
    public void updateUserInfofail() {
        ToastHelper.show(this, "用户信息更新失败");
    }

    @Override
    public void clickIcon(boolean isLeft, int position) {
        if (isLeft && position ==1) {
            finish();
        } else if (!isLeft && position ==1) {
            save();
        }
    }
}
