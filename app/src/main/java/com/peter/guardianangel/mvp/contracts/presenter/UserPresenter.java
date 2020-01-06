package com.peter.guardianangel.mvp.contracts.presenter;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
            public void onSuccess(BaseResponse response, JSONObject responseData) {
                if (response.isSuccess()) {
                    User userinfo = JSON.parseObject(responseData.toJSONString(), User.class);
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
