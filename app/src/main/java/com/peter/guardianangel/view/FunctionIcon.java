package com.peter.guardianangel.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.peter.guardianangel.R;

/**
 *  功能导航栏下的Icon
 */
public class FunctionIcon extends RelativeLayout {

    // Icon是什么功能
    private String mContent;
    // Icon的资源值
    private String mIconResourceValue;

    private TextView tv_content;
    private IconFont if_icon;

    public FunctionIcon(Context context) {
        this(context, null);
    }

    public FunctionIcon(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FunctionIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FunctionIcon(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        getAttribute(context, attrs);

        init(context);

        bindView();
    }

    private void bindView() {
        if (mContent != null) {
            tv_content.setText(mContent);
        }

        if (mIconResourceValue != null) {
            if_icon.setText(mIconResourceValue);
        }
    }

    private void getAttribute(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.function_icon);
        mContent = array.getString(R.styleable.function_icon_icon_content);
        mIconResourceValue = array.getString(R.styleable.function_icon_icon_value);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.function_icon_view, this);

        if_icon = findViewById(R.id.function_icon_view_if_icon);
        tv_content = findViewById(R.id.function_icon_view_tv_content);
    }


}
