package com.peter.guardianangel.mvp.contracts.view;

import com.peter.guardianangel.base.BaseView;

public interface RegisterView extends BaseView {
    void registerSuccess();

    void registerFail(String msg);
}
