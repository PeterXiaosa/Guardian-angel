package com.peter.guardianangel.util;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class ToastHelper {

    private static Toast toast;

    public static void show(Context context, String message){
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int strResId){
        showToast(context, strResId, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, String message){
        showToast(context, message, Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, int strResId){
        showToast(context, strResId, Toast.LENGTH_LONG);
    }


    private static void showToast(Context context, CharSequence msg, int duration){
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, duration);
        // 如果要自定义Toast样式的话
//        toast.setView(new View());

        toast.show();
    }

    private static void showToast(Context context, int strResId, int duration){
        if (toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context, strResId, duration);
        // 如果要自定义Toast样式的话
//        toast.setView(new View());

        toast.show();
    }
}

