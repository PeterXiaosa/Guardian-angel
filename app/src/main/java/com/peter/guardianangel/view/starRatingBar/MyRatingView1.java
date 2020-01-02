package com.peter.guardianangel.view.starRatingBar;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.peter.guardianangel.R;

public class MyRatingView1 implements IRatingView {

    ViewGroup mViewGroup;

    @Override
    public void setCurrentState(int state, int position, int starNums) {

        ImageView ivStar = mViewGroup.findViewById(R.id.iv_star);
        switch (state) {
            case IRatingView.STATE_NONE:
                ivStar.setImageResource(R.drawable.icon_star_none);
                break;
            case IRatingView.STATE_HALF:
            case IRatingView.STATE_FULL:
                ivStar.setImageResource(R.drawable.icon_star_full);

                break;
        }
    }

    @Override
    public ViewGroup getRatingView(Context context, int position, int starNums) {
        View inflate = View.inflate(context, R.layout.my_rating1, null);
        mViewGroup = (ViewGroup) inflate;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        mViewGroup.setLayoutParams(layoutParams);
        return mViewGroup;
    }
}