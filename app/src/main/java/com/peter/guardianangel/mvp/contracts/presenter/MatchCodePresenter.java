package com.peter.guardianangel.mvp.contracts.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.bean.MyLocation;
import com.peter.guardianangel.data.EventMessage;
import com.peter.guardianangel.data.ServiceConstant;
import com.peter.guardianangel.data.SocketClient;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.data.WebSocketConnect;
import com.peter.guardianangel.mvp.contracts.view.MatchCodeView;
import com.peter.guardianangel.retrofit.ApiCallback;
import com.peter.guardianangel.retrofit.BaseResponse;
import com.peter.guardianangel.util.SerializeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.java_websocket.client.WebSocketClient;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class MatchCodePresenter extends BasePresenter<MatchCodeView> {

    WebSocketClient mainWebSocketClient;

    List<String> mDatas = new ArrayList<>();

    String urlStr = "ws://%s:8080/JavaWeb_war_exploded/mywebsocket/%s/%s";

    public MatchCodePresenter(MatchCodeView matchCodeView) {
        attachView(matchCodeView);
    }

    public void register(){
        EventBus.getDefault().register(this);
    }

    public void unRegister() {
        EventBus.getDefault().unregister(this);
    }


    public void getMatchCode() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("account", "fhc");

        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        addSubscription(api.getMatchCode(body), new ApiCallback<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response, JSONObject responseData) {
                if (response.data != null) {
                    Log.d("mymatchcode", "response data: " + response.data.toString());

                    String matchcode = String.valueOf(responseData.get("matchcode"));
                    if (matchcode.contains("\"")) {
                        matchcode = matchcode.replace("\"", "");
                    }
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
        jsonObject.put("matchcode", matchCode);

        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        addSubscription(api.checkMatchCode(body), new ApiCallback<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response, JSONObject responseData) {
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
        SocketClient socketClient = new SocketClient(matchCode);
        socketClient.connect();
    }

    private byte[] decodeValue(ByteBuffer bytes) {
        int len = bytes.limit() - bytes.position();
        byte[] bytes1 = new byte[len];
        bytes.get(bytes1);
        return bytes1;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventMessage messageEvent) {
        int action = messageEvent.getAction();
        Object data = messageEvent.getData();
        String content = messageEvent.getContent();
        switch (action) {
            case ServiceConstant.SERVICE_TYPE_CONNECT_OPEN:
                connectOpen();
                break;
            case ServiceConstant.SERVICE_TYPE_MATCH_SUCCESS:
                connectSuccess();
                break;
            case ServiceConstant.SERVICE_TYPE_MESSAGE_STRING:
                receiveMessage(content);
                break;
            case ServiceConstant.SERVICE_TYPE_MESSAGE_BYTE:
                receiveMessage((ByteBuffer) data);
                break;
            case ServiceConstant.SERVICE_TYPE_CONNECT_CLOSE:
                connectClose(content);
                break;
            case ServiceConstant.SERVICE_TYPE_CONNECT_ERROR:
                connectError();
                break;
        }

    }

    private void connectSuccess() {
        mvpView.jumpMainPage();
    }

    private void connectError() {
        Log.d("peterfu", "连接错误");
        mvpView.connectionError();
    }

    private void connectClose(String content) {
        Log.d("peterfu", "连接关闭");
        mvpView.connectionClose(content);
    }

    private void receiveMessage(String message) {
        mvpView.receiveMessage(message);
    }

    private void receiveMessage(ByteBuffer bytes) {
        MyLocation location = (MyLocation) SerializeUtil.unserialize(decodeValue(bytes));
        UserData.getInstance().setPartnerLocation(location);
        Log.d("getlocation", "location : " + location.toString());
    }

    private void connectOpen() {
        Log.d("peterfu", "连接建立成功");
        mvpView.connectionOpen();
    }
}
