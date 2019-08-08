package com.peter.guardianangel.api;

import android.util.Log;

import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.data.WebSocketConnect;
import com.peter.guardianangel.retrofit.Api;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

public class MyApi {

    private static String urlStr = "ws://%s:8080/mywebsocket/%s/%s";

    private static WebSocketClient mainWebSocketClient;

    public static void connect(String matchCode){
        try {
            String realUrl = String.format(urlStr, Api.IP, matchCode, UserData.getInstance().getDeviceId());
            URI uri = new URI(realUrl);
            mainWebSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
//                    Log.d("peterfu", "连接建立");
//                    mvpView.connectionOpen();
                }


                @Override
                public void onMessage(final String message) {
//                    Log.d("peterfu", "收到消息");
//                    if ("startconnectyourpartner".equals(message)) {
//                        mvpView.jumpMainPage();
//                    } else {
//                        mDatas.add(message);
//                        mvpView.receiveMessage(message);
//                    }
                }

                @Override
                public void onMessage(ByteBuffer bytes) {
                    super.onMessage(bytes);
                }



                @Override
                public void onClose(int code, String reason, boolean remote) {
//                    Log.d("peterfu", "连接关闭");
//                    mvpView.connectionClose(reason);
                }

                @Override
                public void onError(Exception ex) {
//                    Log.d("peterfu", "连接错误");
//                    mvpView.connectionError();
                }
            };
            mainWebSocketClient.connect();
            WebSocketConnect.getInstance().setWebSocketClient(mainWebSocketClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
