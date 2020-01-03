package com.peter.guardianangel.mvp.contracts.view;

import com.peter.guardianangel.base.BaseView;
import com.peter.guardianangel.bean.User;

public interface UserView extends BaseView {
    void updateUserInfo(User user);

    void updateUserInfoFail();
}
