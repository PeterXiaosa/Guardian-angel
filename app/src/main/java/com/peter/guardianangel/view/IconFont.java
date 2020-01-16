package com.peter.guardianangel.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.peter.guardianangel.R;

public class IconFont extends AppCompatTextView {

    private final int defaultTextSize = getResources().getDimensionPixelSize(R.dimen.x60);

    public IconFont(Context context) {
        this(context, null);
    }

    public IconFont(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Typeface font = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
        this.setTypeface(font);

//        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultTextSize);
    }
}
