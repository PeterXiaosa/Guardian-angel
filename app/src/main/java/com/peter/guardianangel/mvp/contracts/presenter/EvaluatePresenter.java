package com.peter.guardianangel.mvp.contracts.presenter;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.bean.Evaluate;
import com.peter.guardianangel.mvp.contracts.view.EvaluateView;
import com.peter.guardianangel.retrofit.ApiCallback;
import com.peter.guardianangel.retrofit.BaseResponse;

public class EvaluatePresenter extends BasePresenter<EvaluateView> {

    public EvaluatePresenter(@NonNull EvaluateView view){
        attachView(view);
        initLoginData();
    }

    private void initLoginData(){

    }

    public void addEvaluate(Evaluate evaluate) {
        addSubscription(api.addEvaluate(evaluate), new ApiCallback<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse response, JSONObject responseData) {
                if (response.isSuccess()) {
                    mvpView.evaluateSuccess();
                }else {
                    mvpView.evaluateFail("error");
                }
            }

            @Override
            public void onFailure(String msg) {
                mvpView.evaluateFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
