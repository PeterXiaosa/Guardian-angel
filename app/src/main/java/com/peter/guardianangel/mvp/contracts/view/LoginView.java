package com.peter.guardianangel.mvp.contracts.view;

import com.peter.guardianangel.base.BaseView;

public interface LoginView extends BaseView {
    void loginSuccess();

    void loginFail(String errorMsg, int errorCode);

    void loginFail(String errorMsg);

    void reflect(String methodName);
}