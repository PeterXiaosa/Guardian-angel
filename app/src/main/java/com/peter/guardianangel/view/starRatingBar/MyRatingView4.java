package com.peter.guardianangel.view.starRatingBar;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peter.guardianangel.R;

public class MyRatingView4 implements IRatingView {

    ViewGroup mViewGroup;

    @Override
    public void setCurrentState(int state, int position, int starNums) {

        ImageView ivStar = mViewGroup.findViewById(R.id.iv_star);
        TextView tvTitle = mViewGroup.findViewById(R.id.tv_title);
        switch (state) {
            case IRatingView.STATE_NONE:
            case IRatingView.STATE_HALF:
                tvTitle.setTextColor(Color.GRAY);
                switch (position) {
                    case 0:
                        ivStar.setImageResource(R.drawable.icon_bronze_none);
                        tvTitle.setText("Bronze");
                        break;
                    case 1:
                        ivStar.setImageResource(R.drawable.icon_silver_none);
                        tvTitle.setText("Silver");
                        break;
                    case 2:
                        ivStar.setImageResource(R.drawable.icon_gold_none);
                        tvTitle.setText("Gold");
                        break;
                }
                break;
            case IRatingView.STATE_FULL:
                switch (position) {
                    case 0:
                        ivStar.setImageResource(R.drawable.icon_bronze);
                        tvTitle.setTextColor(Color.parseColor("#ff330000"));
                        tvTitle.setText("Bronze");
                        break;
                    case 1:
                        ivStar.setImageResource(R.drawable.icon_silver);
                        tvTitle.setTextColor(Color.parseColor("#ffaa0000"));
                        tvTitle.setText("Silver");
                        break;
                    case 2:
                        ivStar.setImageResource(R.drawable.icon_gold);
                        tvTitle.setTextColor(Color.parseColor("#ffff0000"));
                        tvTitle.setText("Gold");
                        break;
                }
                break;
        }
    }

    @Override
    public ViewGroup getRatingView(Context context, int position, int starNums) {
        View inflate = View.inflate(context, R.layout.my_rating4, null);
        mViewGroup = (ViewGroup) inflate;
        switch (position) {
            case 0:
                mViewGroup.findViewById(R.id.view_left).setVisibility(View.INVISIBLE);
                break;
            case 2:
                mViewGroup.findViewById(R.id.view_right).setVisibility(View.INVISIBLE);
                break;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        mViewGroup.setLayoutParams(layoutParams);
        return mViewGroup;
    }
}