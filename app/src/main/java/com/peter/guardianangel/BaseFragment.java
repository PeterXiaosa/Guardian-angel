package com.peter.guardianangel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected ViewGroup mViewGroup;
    protected View mRoot;
    private Unbinder unbinder;

    protected void initData(){}

    protected void initView(View root){}

    protected abstract int getLayoutId();

    protected void register() {
        if(!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
    }

    protected void unRegister(){
        if(EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewGroup = container;
        initData();
        if(mRoot!=null){
            ViewGroup parent = (ViewGroup)mRoot.getParent();
            if(parent!=null){
                parent.removeView(mRoot);
            }
        }else{
            mRoot = inflater.inflate(getLayoutId(),container,false);
            initView(mRoot);
        }
        unbinder = ButterKnife.bind(this, mRoot);

        return mRoot;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }


    @Override
    public void onResume() {
        super.onResume();

//        if(!EventBus.getDefault().isRegistered(this) && !isHidden()) {//加上判断
//            EventBus.getDefault().register(this);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
