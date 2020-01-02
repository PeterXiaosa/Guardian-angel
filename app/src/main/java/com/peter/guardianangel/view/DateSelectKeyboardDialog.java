package com.peter.guardianangel.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.peter.guardianangel.R;
import com.peter.guardianangel.view.daypicker.CustomNumberPicker;

public class DateSelectKeyboardDialog extends Dialog implements View.OnClickListener, DialogInterface.OnShowListener{
    private CustomNumberPicker mYearPicker, mMonthPicker, mDayPicker;
    private TextView mOkButton, mStartTriggerButton;
    private TextView mEdit;
    private onItemSelected mOnItemSelected;
    private Context mContext;

    public interface onItemSelected{
        void timeSelect(int year, int month ,int day);
        void cancel();
    }

    public DateSelectKeyboardDialog(@NonNull Context context, TextView editText, onItemSelected onItemSelected) {
        this(context, R.style.TimeDialog,editText, onItemSelected);
    }

    public DateSelectKeyboardDialog(@NonNull Context context, int themeResId, TextView editText, onItemSelected onItemSelected) {
        super(context, themeResId);
        initWidget(context);
        mContext = context;
        mEdit = editText;
        this.mOnItemSelected = onItemSelected;
    }

    void initWidget(Context context){
        ViewGroup ContentView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.linearlayout_es_date_keyboard_dialog,null);
        setContentView(ContentView);

        bindView();
        bindViewClick();
        initDialogParams(context);
        initPickListener();
    }

    private void initPickListener() {
        final int[] year = new int[1];
        year[0] = mYearPicker.getValue();
        final int[] month = new int[1];
        month[0] = mMonthPicker.getValue();
        mYearPicker.setListener(new CustomNumberPicker.OnEsValueChangeListener() {
            @Override
            public void onValueChanged(int position, Object defaultValue) {
                year[0] = (int)defaultValue;
                mDayPicker.setMaxValue(calculateDayOfYear(year[0], month[0]));
            }
        });
        mMonthPicker.setListener(new CustomNumberPicker.OnEsValueChangeListener() {
            @Override
            public void onValueChanged(int position, Object defaultValue) {
                month[0] = (int) defaultValue;
                mDayPicker.setMaxValue(calculateDayOfYear(year[0], month[0]));
            }
        });
    }

    private int calculateDayOfYear(int year, int month){
        int dayNum = 30;
        if (month ==1 || month == 3 || month == 5 || month ==7 || month ==8 || month == 10 || month == 12){
            dayNum =31;
        }else if (month == 4 || month == 6 || month == 9 || month == 11){
            dayNum = 30;
        }else if (month == 2){
            if (isLeapYear(year)){
                dayNum = 29;
            }else {
                dayNum = 28;
            }
        }
        return dayNum;
    }

    private boolean isLeapYear(int year){
        boolean isLeapYear = false;
        if (year % 4 == 0){
            if (year % 100 == 0){
                if (year % 400 == 0){
                    isLeapYear = true;
                }else{
                    isLeapYear = false;
                }
            }else {
                isLeapYear = true;
            }
        }else {
            isLeapYear = false;
        }
        return isLeapYear;
    }

    private void bindViewClick() {
        mOkButton.setOnClickListener(this);
        mStartTriggerButton.setOnClickListener(this);
    }

    private void initDialogParams(Context context) {
        Window window = this.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.height = (int)context.getResources().getDimension(R.dimen.y889);
        wl.width = context.getResources().getDisplayMetrics().widthPixels;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
        window.setAttributes(wl);
        //设置点击外围消散
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
        this.setOnShowListener(this);
    }

    private void bindView() {
        mOkButton = findViewById(R.id.tv_date_keyboard_done);
        mStartTriggerButton = findViewById(R.id.date_picker_tv_cancel);
        mYearPicker = findViewById(R.id.numpicker_date_keyboard_year);
        mMonthPicker = findViewById(R.id.numpicker_date_keyboard_month);
        mDayPicker = findViewById(R.id.numpicker_date_keyboard_day);
    }

    public void setCancelTitle(String string) {
        mStartTriggerButton.setText(string);
        ViewGroup.LayoutParams params = mStartTriggerButton.getLayoutParams();
        params.width = (int) mContext.getResources().getDimension(R.dimen.x245);
        mStartTriggerButton.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mStartTriggerButton.getId()) {
            mOnItemSelected.cancel();
        } else if (v.getId() == mOkButton.getId()) {
            mOnItemSelected.timeSelect(mYearPicker.getValue(), mMonthPicker.getValue(), mDayPicker.getValue());
        }
        this.dismiss();
    }

    public void refreshTime() {
        if(mEdit == null){
            return;
        }
        String timeStr = mEdit.getText().toString();
        String []timeStrs = timeStr.split("-");
        if(timeStrs.length == 3){
            mYearPicker.setValue(Integer.parseInt(timeStrs[0]));
            mMonthPicker.setValue(Integer.parseInt(timeStrs[1]));
            mDayPicker.setValue(Integer.parseInt(timeStrs[2]));
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        refreshTime();
    }
}
