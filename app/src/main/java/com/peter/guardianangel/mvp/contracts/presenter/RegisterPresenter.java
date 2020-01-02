package com.peter.guardianangel.mvp.contracts.presenter;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.mvp.contracts.view.RegisterView;
import com.peter.guardianangel.retrofit.ApiCallback;
import com.peter.guardianangel.retrofit.BaseResponse;

public class RegisterPresenter extends BasePresenter<RegisterView> {

    public RegisterPresenter(@NonNull RegisterView view){
        attachView(view);
        initLoginData();
    }

    private void initLoginData() {

    }

    public void register(String accouont, String password) {
        User user = new User();
        user.setAccount(accouont);
        user.setPassword(password);
        user.setGenkey(UserData.getInstance().getGenkey());
        user.setDeviceId(UserData.getInstance().getDeviceId());
        user.setMatchcode("123");

        addSubscription(api.register(user), new ApiCallback<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response, JsonObject responseData) {
                if (response.isSuccess()) {
                    mvpView.registerSuccess();
                }else {
                    mvpView.registerFail(response.msg);
                }
            }

            @Override
            public void onFailure(String msg) {
                mvpView.registerFail(msg);
            }

            @Override
            public void onFinish() {
                mvpView.cancelLoading();
            }
        });
    }
}
