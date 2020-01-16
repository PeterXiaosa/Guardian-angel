package com.peter.guardianangel.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.peter.guardianangel.R;

public class MyToolbar extends RelativeLayout implements View.OnClickListener {

    private TextView mTvTitle;

    private String leftIconValue1, leftIconValue2, leftIconValue3;
    private String rightIconValue1, rightIconValue2, rightIconValue3;

    private LinearLayout mLlLeftContainer, mLlRightContainer;
    private RelativeLayout mRlMain;

    private String mTitle;

    private final float mIconSize = getResources().getDimension(R.dimen.x60);

    IToolbarClick iToolbarClick;

    public interface IToolbarClick {
        // 左边第一个图标 true，1
        void clickIcon(boolean isLeft, int position);
    }

    public MyToolbar(Context context) {
        this(context, null);
    }

    public MyToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        getAttribute(context, attrs);

        init(context);

        addIconFontView(context);
    }

    public void setToolbarClick(IToolbarClick iToolbarClick) {
        this.iToolbarClick = iToolbarClick;
    }

    private void getAttribute(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.my_toolbar);
        mTitle = array.getString(R.styleable.my_toolbar_title);
        leftIconValue1 = array.getString(R.styleable.my_toolbar_left_icon1);
        leftIconValue2 = array.getString(R.styleable.my_toolbar_left_icon2);
        leftIconValue3 = array.getString(R.styleable.my_toolbar_left_icon3);
        rightIconValue1 = array.getString(R.styleable.my_toolbar_right_icon1);
        rightIconValue2 = array.getString(R.styleable.my_toolbar_right_icon2);
        rightIconValue3 = array.getString(R.styleable.my_toolbar_right_icon3);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.my_toolbar_view, this);

        mTvTitle = findViewById(R.id.my_toolbar_view_tv_title);
        mLlLeftContainer = findViewById(R.id.my_toolbar_view_ll_left_container);
        mLlRightContainer = findViewById(R.id.my_toolbar_view_ll_right_container);
        mRlMain = findViewById(R.id.my_toolbar_view_rl_main);

        mTvTitle.setText(mTitle);
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.y126));
        params.topMargin = getStatusBarHeight(context);
        mRlMain.setLayoutParams(params);
    }

    private void addIconFontView(Context context) {
        if (leftIconValue1 != null) {
            IconFont iconFont1 = new IconFont(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.x30);
            iconFont1.setLayoutParams(layoutParams);
            iconFont1.setOnClickListener(this);
            iconFont1.setText(leftIconValue1);
            iconFont1.setTextColor(context.getResources().getColor(R.color.white, null));
            iconFont1.setTextSize(TypedValue.COMPLEX_UNIT_PX, mIconSize);
            mLlLeftContainer.addView(iconFont1);
        }

        if (leftIconValue2 != null) {
            IconFont iconFont2 = new IconFont(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.x30);
            iconFont2.setLayoutParams(layoutParams);
            iconFont2.setOnClickListener(this);
            iconFont2.setText(leftIconValue2);
            iconFont2.setTextColor(context.getResources().getColor(R.color.white, null));
            iconFont2.setTextSize(TypedValue.COMPLEX_UNIT_PX, mIconSize);
            mLlLeftContainer.addView(iconFont2);
        }

        if (leftIconValue3 != null) {
            IconFont iconFont3 = new IconFont(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.x30);
            iconFont3.setLayoutParams(layoutParams);
            iconFont3.setOnClickListener(this);
            iconFont3.setText(leftIconValue3);
            iconFont3.setTextColor(context.getResources().getColor(R.color.white, null));
            iconFont3.setTextSize(TypedValue.COMPLEX_UNIT_PX, mIconSize);
            mLlLeftContainer.addView(iconFont3);
        }

        if (rightIconValue1 != null) {
            IconFont iconFont4 = new IconFont(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.x30);
            iconFont4.setLayoutParams(layoutParams);
            iconFont4.setOnClickListener(this);
            iconFont4.setText(rightIconValue1);
            iconFont4.setTextColor(context.getResources().getColor(R.color.white, null));
            iconFont4.setTextSize(TypedValue.COMPLEX_UNIT_PX, mIconSize);
            mLlRightContainer.addView(iconFont4, 0);
        }

        if (rightIconValue2 != null) {
            IconFont iconFont5 = new IconFont(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.x30);
            iconFont5.setLayoutParams(layoutParams);
            iconFont5.setOnClickListener(this);
            iconFont5.setText(rightIconValue2);
            iconFont5.setTextColor(context.getResources().getColor(R.color.white, null));
            iconFont5.setTextSize(TypedValue.COMPLEX_UNIT_PX, mIconSize);
            mLlRightContainer.addView(iconFont5, 0);
        }

        if (rightIconValue3 != null) {
            IconFont iconFont6 = new IconFont(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.x30);
            iconFont6.setLayoutParams(layoutParams);
            iconFont6.setOnClickListener(this);
            iconFont6.setText(rightIconValue3);
            iconFont6.setTextColor(context.getResources().getColor(R.color.white, null));
            iconFont6.setTextSize(TypedValue.COMPLEX_UNIT_PX, mIconSize);
            mLlRightContainer.addView(iconFont6, 0);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof IconFont) {
            String resourceValue = ((IconFont) v).getText().toString();
            if (iToolbarClick == null) {
                return;
            }

            if (resourceValue.equals(leftIconValue1)) {
                iToolbarClick.clickIcon(true, 1);
            } else if (resourceValue.equals(leftIconValue2)) {
                iToolbarClick.clickIcon(true, 2);
            } else if (resourceValue.equals(leftIconValue3)) {
                iToolbarClick.clickIcon(true, 3);
            } else if (resourceValue.equals(rightIconValue1)) {
                iToolbarClick.clickIcon(false, 1);
            } else if (resourceValue.equals(rightIconValue2)) {
                iToolbarClick.clickIcon(false, 2);
            } else if (resourceValue.equals(rightIconValue3)) {
                iToolbarClick.clickIcon(false, 3);
            }
        }
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
