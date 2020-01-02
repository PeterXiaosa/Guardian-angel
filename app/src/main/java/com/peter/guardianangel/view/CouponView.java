package com.peter.guardianangel.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.peter.guardianangel.R;

/**
 * 卡券控件
 */

/**
 * <com.example.couponview.CouponView
 *         android:layout_width="280dp"
 *         android:layout_height="100dp"
 *         android:background="@color/blue"
 *         wbl:radius="10dp"
 *         wbl:spacing="10dp"
 *         android:gravity="center" >
 *
 *         <TextView
 *             android:layout_width="wrap_content"
 *             android:layout_height="wrap_content"
 *             android:text="卡劵"
 *             android:textColor="@color/white"
 *             android:textSize="30dp" />
 *     </com.example.couponview.CouponView>
 */


/**
 * <com.orzangleli.coupon.view.CouponView
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     android:orientation="vertical"
 *     android:background="#FF9933"
 *     coupon:gap="4dp"
 *     coupon:radius="5dp"
 *     coupon:radiusBackgroundColor="#ffffff"
 *     coupon:showHorizontal="true"
 *     coupon:showVertical="true"
 *     android:layout_marginTop="10dp"
 *     >
 *
 *     <TextView
 *         android:layout_width="match_parent"
 *         android:layout_height="40dp"
 *         android:text="购物优惠券"
 *         android:textSize="20sp"
 *         android:textColor="#FFFFFF"
 *         android:layout_marginTop="5dp"
 *         android:layout_marginLeft="15dp"
 *         android:gravity="center_vertical"
 *         />
 *
 *     <TextView
 *         android:layout_width="match_parent"
 *         android:layout_height="wrap_content"
 *         android:text="满50元减10元"
 *         android:textSize="14sp"
 *         android:textColor="#FFFFFF"
 *         android:layout_marginTop="3dp"
 *         android:layout_marginLeft="25dp"
 *         android:gravity="center_vertical"
 *         />
 *     <TextView
 *         android:layout_width="match_parent"
 *         android:layout_height="wrap_content"
 *         android:text="使用期限：一周内"
 *         android:textSize="14sp"
 *         android:textColor="#FFFFFF"
 *         android:layout_marginTop="3dp"
 *         android:layout_marginLeft="25dp"
 *         android:layout_marginBottom="15dp"
 *         android:gravity="center_vertical"
 *         />
 *
 *
 * </com.orzangleli.coupon.view.CouponView>
 */

public class CouponView extends RelativeLayout{

    private Paint mPaint;
    //边缘小半圆的半径
    private float radius = 20;
    //小半圆之间的间距
    private float spacing = 20;
    //左右边距
    private float paddingLeft;
    private float paddingRight;
    //半圆的个数
    private int numCircle;
    //控件的高宽度
    private int height;
    private int width;

    private float remain;

    public CouponView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//		initView(context);
    }

    public CouponView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public CouponView(Context context) {
        super(context);
//		initView(context);
    }

    private void initView(Context context,AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.CouponView);
        float radius = ta.getDimension(R.styleable.CouponView_radius,20);
        float spacing = ta.getDimension(R.styleable.CouponView_spacing, 20);
        setRadius(radius);
        setSpacing(spacing);

        ta.recycle();

        paddingLeft = paddingRight = spacing;
    }


    private void setSpacing(float spacing) {
        this.spacing = spacing;
    }

    private void setRadius(float radius) {
        this.radius = radius;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("这里");
        // getWidth在OnCreat的时候得到的是0. 当一个view对象创建时，android并不知道其大小，所以getWidth()和   getHeight()返回的结果是0，真正大小是在计算布局时才会计算.
        width = this.getWidth();
        height = this.getHeight();
        //圆的数量始终比边距数量少一个，所以总长度减去左边距除以2*radius+spacing的值就是圆的数量
        numCircle = (int) ((width-paddingLeft)/(2*radius+spacing));
        //除以所有圆和边距的所余下的长度
        remain = ((width-paddingLeft)%(2*radius+spacing));
        System.out.println("圆的个数=="+numCircle);
        System.out.println("remain=="+remain);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //remain/2保证左右两边边距一样
        float cx = (paddingLeft+radius+remain/2);
        for(int i=0;i<numCircle;i++){
            canvas.drawCircle(cx, 0, radius, mPaint);
            canvas.drawCircle(cx, height, radius, mPaint);
            cx = (int) (cx+(2*radius+spacing));
        }
    }
}
