package com.peter.guardianangel.mvp.contracts.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.mvp.contracts.view.UserView;
import com.peter.guardianangel.retrofit.ApiCallback;
import com.peter.guardianangel.retrofit.BaseResponse;

public class UserPresenter extends BasePresenter <UserView> {

    public UserPresenter(@NonNull UserView view){
        attachView(view);
        initLoginData();
    }

    private void initLoginData(){

    }

    public void getUserInfo(){
        User user = UserData.getInstance().getUser();
        addSubscription(api.queryUserInfo(user), new ApiCallback<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response, JsonObject responseData) {
                if (response.isSuccess()) {
                    User userinfo = new Gson().fromJson(responseData.toString(), User.class);
                    UserData.getInstance().setUser(userinfo);
                    mvpView.updateUserInfo(userinfo);
                }else {
                    mvpView.updateUserInfoFail();
                }
            }

            @Override
            public void onFailure(String msg) {
                mvpView.updateUserInfoFail();
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
