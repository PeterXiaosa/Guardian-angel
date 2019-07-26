package com.peter.guardianangel;

import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.retrofit.Api;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class SocketClient {
    public static WebSocketClient webSocketClient;

    private final static String urlStr = "ws://%s:8080/mywebsocket/%s/%s";

    public static void socketConnect(String matchCode){
        String realUrl = String.format(urlStr, Api.IP, matchCode, UserData.getInstance().getDeviceId());
        try {
            URI uri = new URI(realUrl);
            webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {

                }

                @Override
                public void onMessage(String message) {

                }

                @Override
                public void onClose(int code, String reason, boolean remote) {

                }

                @Override
                public void onError(Exception ex) {

                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
