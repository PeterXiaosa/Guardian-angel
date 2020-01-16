package com.peter.guardianangel.mvp.contracts.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.mvp.contracts.view.LoginView;
import com.peter.guardianangel.retrofit.ApiCallback;
import com.peter.guardianangel.retrofit.BaseResponse;

// Presenter 中保留View的引用, 通过Model去进行数据存储，同时通过Callback进行Model与Presenter的交互
public class LoginPresenter extends BasePresenter<LoginView> {

    private final String TAG = "LoginPresenter";

    public LoginPresenter(@NonNull LoginView view){
        attachView(view);
        initLoginData();
    }

    private void initLoginData(){

    }

    public void login(final User user) {
        addSubscription(api.login(user), new ApiCallback<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse response, JSONObject responseData) {
                if (response.isSuccess()) {
                    User userinfo = JSON.parseObject(responseData.toJSONString(), User.class);
                    UserData.getInstance().setUser(userinfo);
                    Log.d(TAG, userinfo.toString());
                    String partnerAccount = userinfo.getPartnerAccount();
                    String loveAuth = userinfo.getLoveAuth();

                    mvpView.loginSuccess(partnerAccount != null && loveAuth != null && !partnerAccount.isEmpty() && !loveAuth.isEmpty());
                }else {
                    mvpView.loginFail(response.msg);
                }
            }

            @Override
            public void onFailure(String msg) {
                mvpView.loginFail(msg);
            }

            @Override
            public void onFinish() {
                mvpView.cancelLoading();
            }
        });
    }

//    public void login(final UserInfo userInfo, final String methodName) {
//        mvpView.showLoading();
//        addSubscription(api.login(userInfo),
//                new ApiCallback<LoginModel>() {
//                    @Override
//                    public void onSuccess(LoginModel model) {
//                        // 获取数据成功去更新View
//                        if (model.getStatus() == 0){
//                            model.saveLoginUserInfo();
//                            mvpView.loginSuccess();
//                        }else {
//                            model.saveLoginUserAccountName(userInfo.getAccount());
//                            mvpView.loginFail(model.getMsg(), model.getStatus());
//                            errorCodeSubscription(model.getStatus(), new errorCallback() {
//                                @Override
//                                public void onSuccess(ServerBean model) {
//                                    if (model.getStatus() == 0){
//                                        // 重新调用原方法继续之前操作
//                                        mvpView.reflect(methodName);
//                                    }else {
//
//                                    }
//                                }
//
//                                @Override
//                                public void onFail(String msg) {
//
//                                }
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        mvpView.loginFail(msg);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        mvpView.cancelLoading();
//                    }
//                });
//    }
}