package com.peter.guardianangel.mvp.contracts.presenter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.mvp.contracts.view.MatchCodeView;
import com.peter.guardianangel.retrofit.ApiCallback;
import com.peter.guardianangel.retrofit.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;

public class MatchCodePresenter extends BasePresenter<MatchCodeView> {

    public MatchCodePresenter(MatchCodeView matchCodeView) {
        attachView(matchCodeView);
    }

    public void getMatchCode(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", "fhc");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        addSubscription(api.getMatchCode(body), new ApiCallback<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response, JsonObject responseData) {
                if (response.data != null) {
                    String matchcode = String.valueOf(responseData.get("matchcode"));
                    mvpView.updateMatchCode(matchcode);
                }
            }

            @Override
            public void onFailure(String msg) {
                mvpView.updateMatchCode("error code" + msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
