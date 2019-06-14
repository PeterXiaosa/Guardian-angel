package com.peter.guardianangel.annotation;

import android.app.Activity;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class ListenerInvocationHandler implements InvocationHandler {

    Activity activity;
    Map<String,Method> methodMap;

    public ListenerInvocationHandler(Activity activity, Map<String,Method> methodMap) {
        this.activity = activity;
        this.methodMap = methodMap;
        Log.i("castiel", "打印方法Map：" + methodMap.toString());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // method就是我们的CastielOnClickInject
        String name = method.getName();
        Log.i("castiel", "打印方法name：" + name);
        Method mtd = methodMap.get(name);
        if (mtd != null) {
            return mtd.invoke(activity, args);
        }
        return method.invoke(activity, args);
    }

}