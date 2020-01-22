package com.peter.guardianangel.mvp.contracts.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.mvp.contracts.view.ProtectView;
import com.peter.guardianangel.retrofit.ApiCallback;
import com.peter.guardianangel.retrofit.BaseResponse;

public class ProtectPresenter extends BasePresenter {

    public ProtectPresenter(ProtectView protectView) {
        attachView(protectView);
    }

    public void queryUserInfo(User user) {
        addSubscription(api.queryUserInfo(user), new ApiCallback<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response, JSONObject responseData) {
                if (response.isSuccess()) {
                    User userinfo = JSON.parseObject(responseData.toJSONString(), User.class);
                    UserData.getInstance().setUser(userinfo);
                }
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
