package com.peter.guardianangel.mvp.contracts.presenter;

import android.support.annotation.NonNull;

import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.mvp.contracts.view.UserInfoEditView;

public class UserInfoEditPresenter extends BasePresenter <UserInfoEditView> {
    public UserInfoEditPresenter(@NonNull UserInfoEditView view){
        attachView(view);
    }
}
