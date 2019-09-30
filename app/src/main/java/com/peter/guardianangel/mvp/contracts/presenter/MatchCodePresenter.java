package com.peter.guardianangel.mvp.contracts.presenter;

import android.util.Log;

import com.google.gson.JsonObject;
import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.bean.MyLocation;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.data.WebSocketConnect;
import com.peter.guardianangel.mvp.contracts.view.MatchCodeView;
import com.peter.guardianangel.retrofit.Api;
import com.peter.guardianangel.retrofit.ApiCallback;
import com.peter.guardianangel.retrofit.BaseResponse;
import com.peter.guardianangel.util.SerializeUtil;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.nio.ByteBuffer;
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

    public void getMatchCode() {
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
                    Log.d("mymatchcode", "response data: " + response.data.toString());
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
                WebSocketConnect.getInstance().disConnect();
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

    private void longConnectConfig(String matchCode) {
        try {
            String realUrl = String.format(urlStr, Api.IP, matchCode, UserData.getInstance().getDeviceId());
            URI uri = new URI(realUrl);

            WebSocketConnect.getInstance().connect(uri, new WebSocketConnect.SocketCallback() {
                @Override
                public void onOpen() {
                    Log.d("peterfu", "连接建立");
                    mvpView.connectionOpen();
                }

                @Override
                public void onMessage(String message) {
                    Log.d("peterfu", "收到消息");
                    if ("startconnectyourpartner".equals(message)) {
                        mvpView.jumpMainPage();
                    } else {
                        mvpView.receiveMessage(message);
                    }
                }

                @Override
                public void onMessage(ByteBuffer bytes) {
                    MyLocation location = (MyLocation) SerializeUtil.unserialize(decodeValue(bytes));
                    UserData.getInstance().setPartnerLocation(location);
                    Log.d("getlocation", "location : " + location.toString());
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
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] decodeValue(ByteBuffer bytes) {
        int len = bytes.limit() - bytes.position();
        byte[] bytes1 = new byte[len];
        bytes.get(bytes1);
        return bytes1;
    }
}
