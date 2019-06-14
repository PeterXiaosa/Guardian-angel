package com.peter.guardianangel.annotation;

public @interface PeterEventBase {
    String listenerSetter();
    Class<?> listenerType();
    String callbackMethod();
}
