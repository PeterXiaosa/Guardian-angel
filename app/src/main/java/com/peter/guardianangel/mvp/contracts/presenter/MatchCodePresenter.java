package com.peter.guardianangel.mvp.contracts.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.mvp.contracts.view.MatchCodeView;
import com.peter.guardianangel.retrofit.Api;
import com.peter.guardianangel.retrofit.ApiCallback;
import com.peter.guardianangel.retrofit.BaseResponse;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class MatchCodePresenter extends BasePresenter<MatchCodeView> {

    WebSocketClient mainWebSocketClient;

    List<String> mDatas = new ArrayList<>();

    String urlStr = "ws://%s:8080/mywebsocket/%s/%s";

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

    public void checkMatchCode(final String matchCode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("matchcode", matchCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        addSubscription(api.checkMatchCode(body), new ApiCallback<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response, JsonObject responseData) {
                longConnectConfig(matchCode);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.errorMatchCode();
            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void longConnectConfig(String matchCode){
        try {
            String realUrl = String.format(urlStr, Api.IP, matchCode, UserData.getInstance().getDeviceId());
            URI uri = new URI(realUrl);
            mainWebSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("peterfu", "连接建立");
                    mvpView.connectionOpen();
                }

                @Override
                public void onMessage(final String message) {
                    Log.d("peterfu", "收到消息");
                    if ("startconnectyourpartner".equals(message)) {
                        mvpView.jumpMainPage();
                    }else {
                        mDatas.add(message);
                        mvpView.receiveMessage(message);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("peterfu", "连接关闭");
                    mvpView.connectionClose(reason);
                }

                @Override
                public void onError(Exception ex) {
                    Log.d("peterfu", "连接错误");
                    mvpView.connectionError();
                }
            };
            mainWebSocketClient.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
