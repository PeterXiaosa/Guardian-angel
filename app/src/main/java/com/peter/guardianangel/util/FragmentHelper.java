package com.peter.guardianangel.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.peter.guardianangel.R;

public class FragmentHelper {
    private static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;

    private static int lastShowFragment = 0;

    private static FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    private static void setFragmentManager(FragmentManager fragmentManager) {
        FragmentHelper.fragmentManager = fragmentManager;
    }

    public static FragmentTransaction getFragmentTransaction() {
        return fragmentTransaction;
    }

    public static void setFragmentTransaction(FragmentTransaction fragmentTransaction) {
        FragmentHelper.fragmentTransaction = fragmentTransaction;
    }

    private static void initFragmentTransaction() {
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    /**
     * 此方法在onBackPressed被重写时使用
     * 回退到上一层fragment
     * 如果已经是最后一层，隐藏界面
     * @param activity 当前activity，仅支持AppCompatActivity
     *                 在fragment中请使用(AppCompatActivity)getActivity()作为参数传入
     */
    public static void back(AppCompatActivity activity) {
        if (getFragmentManager().getBackStackEntryCount() <= 1) {
            activity.moveTaskToBack(true);
        }else{
            fragmentManager.popBackStack();
        }
    }

    public static void initFragment(Fragment fragment, AppCompatActivity activity) {
        FragmentHelper.setFragmentManager(activity.getSupportFragmentManager());
        FragmentHelper.initFragmentTransaction();

        lastShowFragment = 2;
        fragmentTransaction.add(R.id.activity_protect_fl_container, fragment)
                .show(fragment)
                .commit();
    }

    /**
     * 切换Fragment为传入参数
     *
     * @param activity 当前activity，仅支持AppCompatActivity
     *                 在fragment中请使用(AppCompatActivity)getActivity()作为参数传入
     * @param fragment 目标fragment对象
     */
    public static void switchFragment(Fragment fragment, AppCompatActivity activity) {
        FragmentHelper.setFragmentManager(activity.getSupportFragmentManager());
        FragmentHelper.initFragmentTransaction();
        //frame容器id
        fragmentManager.findFragmentById(R.id.activity_protect_fl_container);
        fragmentTransaction
                .replace(R.id.activity_protect_fl_container, fragment)
                .addToBackStack(null)
//                .commit();//替换成下面那句可以在frameLayout容器被遮挡的情况下替换fragment
                .commitAllowingStateLoss();
    }
}
