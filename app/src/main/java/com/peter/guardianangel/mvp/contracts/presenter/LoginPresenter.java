package com.peter.guardianangel.mvp.contracts.presenter;

import android.support.annotation.NonNull;

import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.mvp.contracts.model.LoginModel;
import com.peter.guardianangel.mvp.contracts.view.LoginView;
import com.peter.guardianangel.retrofit.ApiCallback;
import com.peter.guardianangel.retrofit.BaseResponse;

import org.json.JSONObject;

import okhttp3.RequestBody;

// Presenter 中保留View的引用, 通过Model去进行数据存储，同时通过Callback进行Model与Presenter的交互
public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter(@NonNull LoginView view){
        attachView(view);
        initLoginData();
    }

    private void initLoginData(){

    }

    public void login(User user) {
        addSubscription(api.login(user), new ApiCallback<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse response) {
                if (response.isSuccess()) {
                    mvpView.loginSuccess();
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