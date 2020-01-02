package com.peter.guardianangel.mvp.contracts.presenter;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.mvp.contracts.view.UserInfoEditView;
import com.peter.guardianangel.retrofit.ApiCallback;
import com.peter.guardianangel.retrofit.BaseResponse;

public class UserInfoEditPresenter extends BasePresenter <UserInfoEditView> {
    public UserInfoEditPresenter(@NonNull UserInfoEditView view){
        attachView(view);
    }

    public void updateUserInfo(User user) {
        addSubscription(api.updateUserInfo(user), new ApiCallback<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse response, JsonObject responseData) {
                if (response.isSuccess()) {
                    mvpView.updateUserInfoSuccessfully();
                } else {
                    mvpView.updateUserInfofail();
                }
            }

            @Override
            public void onFailure(String msg) {
                mvpView.updateUserInfofail();
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
