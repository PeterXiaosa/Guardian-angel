package com.peter.guardianangel.view.daypicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;

import com.peter.guardianangel.R;

import java.util.Arrays;
import java.util.Locale;


public class CustomNumberPicker extends View {
    private static final String TAG = "EsNumberPicker";
    private Context mContext;

    private Paint mTextPaint;
    private Paint mBgPaint;
    private int mMinValue;
    private int mMaxValue;
    private int mPageSize;
    private int mItemHeight;
    private float mNumberPaddingStart;
    private String mSelectedNumberTitle;
    @DimenRes
    private int mTextSize;
    private int mLastTouchY;
    private int mActivePointerId;
    private Object[] mSelector;
    private OverScroller mOverScroller;
    private VelocityTracker mVelocityTracker;

    private boolean mIsDragging;
    private int mTouchSlop;
    private int mMaxVel;
    private int mMinVel;

    @ColorInt
    private int mTextColorNormal;
    @ColorInt
    private int mTextColorNormalShader;
    @ColorInt
    private int mTextColorSelected;
    @ColorInt
    private int mBgColorSelected;

    private LinearGradient mTextGradientUp;
    private LinearGradient mTextGradientDown;

    private OnEsValueChangeListener mCallback;

    private Rect itemRect;
    private Rect selectedRect;
    private int defaultPos = 0;
    private int oldPos = 2;

    public interface OnEsValueChangeListener{
        void onValueChanged(int position, Object Value);
    }

    public void setListener(OnEsValueChangeListener valueChangeListener){
        mCallback = valueChangeListener;
    }

    public CustomNumberPicker(Context context) {
        super(context);
        init(context,null);
    }

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);
    }

    public CustomNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomNumberPicker);
        mTextSize = (int)array.getDimension(R.styleable.CustomNumberPicker_textSize,48);
        mMaxValue = array.getInt(R.styleable.CustomNumberPicker_MaxValue,1);
        mMinValue = array.getInt(R.styleable.CustomNumberPicker_MinValue,0);
        if(mMaxValue <= mMinValue){
            mMaxValue = mMinValue ;
        }
        mSelector = new Integer[mMaxValue-mMinValue+1];
        for (int i = 0; i < mMaxValue - mMinValue + 1; i++) {
            mSelector[i] = mMinValue + i;
        }
        mPageSize = array.getInt(R.styleable.CustomNumberPicker_pageSize,5);
        mTextColorNormal = array.getColor(R.styleable.CustomNumberPicker_textColorGrey,getResources().getColor(R.color.gray, null));
        mTextColorSelected = array.getColor(R.styleable.CustomNumberPicker_textColorSelected,getResources().getColor(R.color.gray, null));
        mBgColorSelected = array.getColor(R.styleable.CustomNumberPicker_bgColorSelected, getResources().getColor(R.color.custom_number_picker_selected_default_bg, null));
        mTextColorNormalShader = array.getColor(R.styleable.CustomNumberPicker_textColorGreyShader,getResources().getColor(R.color.custom_number_picker_shader_color, null));
        mNumberPaddingStart = array.getDimension(R.styleable.CustomNumberPicker_numberPaddingStart,getResources().getDimension(R.dimen.x3));
        mSelectedNumberTitle = array.getString(R.styleable.CustomNumberPicker_selectedNumberTitle);
        array.recycle();

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        mBgPaint = new Paint();
        mBgPaint.setStyle(Paint.Style.FILL);

        mBgPaint.setColor(mBgColorSelected);

        mOverScroller = new OverScroller(context,new DecelerateInterpolator());
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaxVel = configuration.getScaledMaximumFlingVelocity();
        mMinVel = configuration.getScaledMinimumFlingVelocity();
        itemRect = new Rect();
        selectedRect = new Rect();

        mTextGradientUp = new LinearGradient(0,0,0,0,mTextColorNormal,mTextColorNormal, Shader.TileMode.CLAMP);
        mTextGradientDown = new LinearGradient(0,0,0,0,mTextColorNormal,mTextColorNormal, Shader.TileMode.CLAMP);

        smoothScrollTo(defaultPos);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mSelector == null||mSelector.length<1){
            return;
        }
        int width = getWidth();
        int height = mItemHeight;
        int itemHeight = getItemHeight();
        int textHeight = computeTextHeight();
        int centerY = getScrollY() + height / 2;
        int selectedPos = computePosition();

        selectedRect.set(0,centerY-itemHeight/2,width,centerY+itemHeight/2);
        //画背景
        canvas.save();
        canvas.drawRect(selectedRect,mBgPaint);
        canvas.restore();
        for(int itemIndex = 0; itemIndex < mSelector.length; itemIndex++){
            itemRect.set(0,itemIndex * itemHeight,width,itemIndex * itemHeight + itemHeight);
            if (itemIndex == selectedPos) {
                mTextPaint.setColor(mTextColorSelected);
            } else {
                mTextPaint.setColor(mTextColorNormal);
            }

            if(itemIndex == selectedPos - mPageSize / 2){
                mTextPaint.setShader(mTextGradientUp);
            }else if(itemIndex == selectedPos + mPageSize / 2){
                mTextPaint.setShader(mTextGradientDown);
            }else {
                mTextPaint.setShader(null);
            }
            canvas.save();
            int y = (itemRect.top + itemRect.bottom - textHeight) / 2;
            String showText;
            if(mSelector[itemIndex] instanceof Integer){
                showText = String.format(Locale.getDefault(),"%02d",(Integer)mSelector[itemIndex]);
            }else if(mSelector[itemIndex] instanceof String){
                showText = (String)mSelector[itemIndex];
            }else {
                showText = mSelector[itemIndex].toString();
            }

            float textWidth = mTextPaint.measureText(showText);
            if(itemIndex == selectedPos){
                //写title
                canvas.drawText(mSelectedNumberTitle, mNumberPaddingStart+textWidth, centerY - textHeight/2, mTextPaint);
            }
            //写数字
            canvas.drawText(showText, mNumberPaddingStart, y, mTextPaint);

            canvas.restore();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        if(lp == null) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = calculateSize(getSuggestedMinimumWidth(),lp.width,widthMeasureSpec);
        int height = calculateSize(getSuggestedMinimumHeight(),lp.height,heightMeasureSpec);
        width += getPaddingLeft() +  getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(width,height);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            mItemHeight = getHeight();
            int itemHeight = getItemHeight();
            mTextGradientUp = new LinearGradient(0,itemHeight,0,0,mTextColorNormal,mTextColorNormalShader, Shader.TileMode.REPEAT);
            mTextGradientDown = new LinearGradient(0,mItemHeight-itemHeight,0,mItemHeight,mTextColorNormal,mTextColorNormalShader, Shader.TileMode.REPEAT);
        }
    }

    /**
     * @param suggestedSize 最合适的大小
     * @param paramSize     配置的大小
     */
    private int calculateSize(int suggestedSize, int paramSize, int measureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);

        switch (MeasureSpec.getMode(mode)) {
            case MeasureSpec.AT_MOST:
                if (paramSize == ViewGroup.LayoutParams.WRAP_CONTENT)
                    result = Math.min(suggestedSize, size);
                else if (paramSize == ViewGroup.LayoutParams.MATCH_PARENT)
                    result = size;
                else {
                    result = Math.min(paramSize, size);
                }
                break;
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                if (paramSize == ViewGroup.LayoutParams.WRAP_CONTENT || paramSize == ViewGroup.LayoutParams.MATCH_PARENT)
                    result = suggestedSize;
                else {
                    result = paramSize;
                }
                break;
        }

        return result;
    }


    @Override
    protected int getSuggestedMinimumHeight() {
        int suggested = super.getSuggestedMinimumHeight();
        if(mSelector != null && mSelector.length > 0 && mPageSize > 0){
            Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
            int height = fontMetricsInt.descent - fontMetricsInt.ascent;
            suggested = Math.max(suggested,height * mPageSize);
        }
        return suggested;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mVelocityTracker != null){
            mVelocityTracker.addMovement(event);
        }
        int action = event.getActionMasked();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if(!mOverScroller.isFinished())
                    mOverScroller.abortAnimation();
                if(mVelocityTracker == null){
                    mVelocityTracker = VelocityTracker.obtain();
                }else {
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(event);
//                mActivePointerId = event.getPointerId(0);
                mActivePointerId = 0;
                mLastTouchY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = (int)(mLastTouchY -  event.getY(mActivePointerId));
                if(!mIsDragging && Math.abs(deltaY) > mTouchSlop){
                    final ViewParent parent = getParent();
                    if(parent != null){
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    if(deltaY > 0){
                        deltaY -= mTouchSlop;
                    }else {
                        deltaY += mTouchSlop;
                    }
                    mIsDragging = true;
                }
                if(mIsDragging){
                    if(canScroll(deltaY)){
                        scrollBy(0,deltaY);
                    }
                    mLastTouchY = (int) event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mIsDragging){
                    mIsDragging = false;
                    final ViewParent parent = getParent();
                    if(parent != null){
                        parent.requestDisallowInterceptTouchEvent(false);
                    }
                    mVelocityTracker.computeCurrentVelocity(1000,mMaxVel);
                    int velocity = (int) mVelocityTracker.getYVelocity(mActivePointerId);
                    if(Math.abs(velocity) > mMinVel){
                        mOverScroller.fling(getScrollX(),getScrollY(),0,-velocity,0,0,
                                getMinimumScrollY() , getMaximumScrollY() , 0, 0);
                        postInvalidateOnAnimation();
                    }else {
                        adjustItem();
                    }
                    recyclerVelocityTracker();
                }else {
                    int y = (int) event.getY(mActivePointerId);
                    handlerClick(y);
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                if(mIsDragging){
                    adjustItem();
                    mIsDragging = false;
                }
                recyclerVelocityTracker();
                break;
        }
        return true;
    }
    private void recyclerVelocityTracker() {

        if (mVelocityTracker != null)
            mVelocityTracker.recycle();

        mVelocityTracker = null;
    }

    private void invalidateOnAnimation() {
        postInvalidateOnAnimation();
    }


    /**
     * 点击滑动
     *
     * @param y 在view视图中点击的Y坐标
     */
    private void handlerClick(int y) {
        y = y + getScrollY();
        int position = y / getItemHeight();
        if (y >= 0 && position < mSelector.length) {
            Rect actualLoc = getLocationByPosition(position);
            int scrollY = actualLoc.top - getScrollY();
            mOverScroller.startScroll(getScrollX(), getScrollY(), 0, scrollY);
            invalidateOnAnimation();
        }
    }



    private Rect getLocationByPosition(int position) {
        int scrollY = position * getItemHeight() + getMinimumScrollY();
        return new Rect(0, scrollY, getWidth(), scrollY + getItemHeight());
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断mOverScroller是否执行完毕
        if (mOverScroller.computeScrollOffset()) {
            int x = mOverScroller.getCurrX();
            int y = mOverScroller.getCurrY();
            scrollTo(x, y);
            //通过重绘来不断调用computeScroll
            invalidate();
        } else if (!mIsDragging) {
            //align item
            adjustItem();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int position = computePosition();
        if (position != oldPos)
            if (mCallback != null ) {
                mCallback.onValueChanged(position, mSelector[position]);
            }
        oldPos = position;
    }

    public void smoothScrollTo(final int position) {
        if (position < 0 || mSelector == null || position > mSelector.length)
            return;
        Rect actualLoc = getLocationByPosition(position);
        int scrollY = actualLoc.top - getScrollY();
        mOverScroller.startScroll(getScrollX(), getScrollY(), 0, scrollY);
        invalidateOnAnimation();
    }




    public void setMaxValue(int maxValue){
        if(maxValue == mMaxValue){
            return;
        }
        int posValue = computePosition() + mMinValue;
        if(maxValue<mMinValue){
            mMinValue = maxValue ;
        }
        mMaxValue = maxValue;
        mSelector = new Integer[mMaxValue-mMinValue+1];
        for (int i = 0; i < mMaxValue - mMinValue + 1; i++) {
            mSelector[i] = mMinValue + i;
        }
        invalidate();

        Rect rect;
        if(posValue>=mMinValue&&posValue<=mMaxValue){
            rect = getLocationByPosition(posValue-mMinValue);
        }else if(posValue<mMinValue){
            rect = getLocationByPosition(0);
        }else {
            rect = getLocationByPosition(mSelector.length-1);
        }
        setScrollY(rect.centerY() - getItemHeight());
    }

    public void setMinValue(int minValue){
        if(minValue == mMinValue){
            return;
        }
        int posValue = computePosition()+mMinValue;
        if(minValue>mMaxValue){
            mMaxValue = minValue;
        }
        mMinValue = minValue;
        mSelector = new Integer[mMaxValue-mMinValue+1];
        for (int i = 0; i < mMaxValue - mMinValue + 1; i++) {
            mSelector[i] = mMinValue + i;
        }
        invalidate();

        Rect rect;
        if(posValue>=mMinValue&&posValue<=mMaxValue){
            rect = getLocationByPosition(posValue-mMinValue);
        }else if(posValue<mMinValue){
            rect = getLocationByPosition(0);
        }else {
            rect = getLocationByPosition(mSelector.length-1);
        }
        setScrollY(rect.centerY() - getItemHeight());
    }

    public void smoothScrollToValue(Object object) {
        if (mSelector == null)
            return;

        int pos = Arrays.binarySearch(mSelector, object);
        smoothScrollTo(pos);
    }

    /**
     * 调整item使对齐居中
     */
    private void adjustItem() {
        int position = computePosition();
        Rect rect = getLocationByPosition(position);
        int scrollY = rect.top - getScrollY();

        if (scrollY != 0) {
            mOverScroller.startScroll(getScrollX(), getScrollY(), 0, scrollY,150);
            invalidateOnAnimation();
        }
    }

    /**
     * 获取当前位置
     * @param offset 当前位置偏移多少
     * @return
     */
    private int computePosition(int offset) {
        int topOffset = mItemHeight / 2;
        int scrollY = getScrollY() + topOffset + offset;
        return scrollY / getItemHeight();
    }
    /**
     * 计算可视区域内每个item的高度
     *
     * @return
     */
    public int getItemHeight() {
        return mItemHeight / mPageSize;
    }

    /**
     * 计算字符串的高度
     *
     * @return
     */
    private int computeTextHeight() {
        Paint.FontMetricsInt metricsInt = mTextPaint.getFontMetricsInt();
        return metricsInt.bottom + metricsInt.top;
    }


    /**
     * 计算当前显示的位置
     *
     * @return
     */
    public int computePosition() {
        return computePosition(0);
    }


    private boolean canScroll(int deltaY) {
        int scrollY = getScrollY() + deltaY ;
        int top = getMinimumScrollY();
        int bottom = getMaximumScrollY();
        return scrollY >= top && scrollY <= bottom;
    }

    private int getMaximumScrollY() {
        return (mSelector.length - 1) * getItemHeight() + getMinimumScrollY();
    }

    private int getMinimumScrollY() {
        return -((mItemHeight - getItemHeight()) / 2);
    }

    public int getValue(){
        return (Integer) mSelector[oldPos];
    }

    public void setValue(int val){
        if(val<mMinValue||val>mMaxValue){
            return;
        }
        int pos = val - mMinValue;
        smoothScrollTo(pos);
    }

    public void setRawValue(Object value){
        int index = 0;
        for (int i = 0; i < mSelector.length; i++){
            if (mSelector[i].equals(value)){
                index = i;
                break;
            }
        }
        smoothScrollTo(index);
    }

    /**
     * 如果設置的不是int數組，那麼不可用setValue和getValue以及setMaxValue和setMinValue
     * @param selector
     */
    public void setSelector(Object[] selector){
        mSelector = selector;
        setScrollY(0);
        invalidate();
    }

    public Object getRawValue(){
        return mSelector[oldPos];
    }

    public int getPosition() {
        return oldPos;
    }
}
