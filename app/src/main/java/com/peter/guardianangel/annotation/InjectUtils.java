package com.peter.guardianangel.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class InjectUtils {
    public static void inject(Activity activity) {
        // 注入布局
        injectLayout(activity);
        // 注入视图
        injectViews(activity);
        // 注入事件
        injectEvents(activity);
    }

    private static void injectLayout(Activity activity) {
        // 获取我们自定义类CastielContentViewInject上面的注解
        Class<? extends Activity> myClass = activity.getClass();
        PeterContentViewInject myContentView = myClass.getAnnotation(PeterContentViewInject.class);
        int myLayoutResId = myContentView.value();
        activity.setContentView(myLayoutResId);
    }

    private static void injectViews(Activity activity) {
// 获取每一个属性上的注解
        Class<? extends Activity> myClass = activity.getClass();
        Field[] myFields = myClass.getDeclaredFields();// 先拿到里面所有的成员变量
        for (Field field : myFields) {
            PeterViewInject myView = field.getAnnotation(PeterViewInject.class);
            if (myView != null) {
                int value = myView.value();// 拿到属性id
                View view = activity.findViewById(value);
                // 将view赋值给类里面的属性
                try {
                    field.setAccessible(true);// 为了防止其实私有的的，需要设置允许访问
                    field.set(activity,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectEvents(Activity activity) {
        Class<? extends Activity> myClass = activity.getClass();
        Method[] myMethod = myClass.getDeclaredMethods();
        for (Method method : myMethod){
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations){
                Class<? extends Annotation> annotationType = annotation.annotationType();
                PeterEventBase peb = annotationType.getAnnotation(PeterEventBase.class);
                String listenerSetter = peb.listenerSetter();
                Class<?> listenerType = peb.listenerType();
                String callbackMethod = peb.callbackMethod();
                try{
                    Method valueMethod = annotationType.getDeclaredMethod("value");
                    try{
                        int[] viewIds = (int[]) valueMethod.invoke(annotation);
                        for (int viewId : viewIds){
                            View view = activity.findViewById(viewId);
                            Method setListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
                            Map<String, Method> methodMap = new HashMap<>();
                            methodMap.put(callbackMethod, method);
                            InvocationHandler invocationHandler = new ListenerInvocationHandler(activity, methodMap);
                            Object newProxyInstance = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, invocationHandler);
                            setListenerMethod.invoke(view, newProxyInstance);
                        }
                    }catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }catch (NoSuchMethodException e){

                }
            }
        }
    }
}
