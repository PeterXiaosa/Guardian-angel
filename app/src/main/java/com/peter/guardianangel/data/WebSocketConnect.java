package com.peter.guardianangel.data;

import android.util.Log;

import com.peter.guardianangel.bean.MyLocation;
import com.peter.guardianangel.util.SerializeUtil;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

public class WebSocketConnect {

    public interface SocketCallback{
        void onOpen();

        void onMessage(String message);

        void onMessage(ByteBuffer bytes);

        void onClose(int code, String reason, boolean remote);

        void onError(Exception ex);
    }

    private WebSocketConnect(){}

    public static WebSocketConnect getInstance(){
        return WebSocketConnectInner.instance;
    }

    private static class WebSocketConnectInner{
        private static WebSocketConnect instance = new WebSocketConnect();
    }

    private WebSocketClient webSocketClient;


    public WebSocketClient getWebSocketClient() {
        return webSocketClient;
    }

    public void setWebSocketClient(WebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    public void connect(URI uri, final SocketCallback socketCallback) {
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                socketCallback.onOpen();
            }


            @Override
            public void onMessage(final String message) {
                socketCallback.onMessage(message);
            }

            @Override
            public void onMessage(ByteBuffer bytes) {
                socketCallback.onMessage(bytes);
            }



            @Override
            public void onClose(int code, String reason, boolean remote) {
                socketCallback.onClose(code, reason, remote);
            }

            @Override
            public void onError(Exception ex) {
                socketCallback.onError(ex);
            }
        };
        webSocketClient.connect();
    }

    public void disConnect(){
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.close();
        }
    }
}
