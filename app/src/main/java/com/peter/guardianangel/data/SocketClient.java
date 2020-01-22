package com.peter.guardianangel.data;

import android.util.Log;

import com.peter.guardianangel.retrofit.Api;
import com.peter.guardianangel.util.SerializeUtil;

import org.greenrobot.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

/**
 *  长连接客户端
 */
public class SocketClient {

    private final String TAG = getClass().getSimpleName();

    private static String urlStr = "ws://%s:8080/JavaWeb_war_exploded/mywebsocket/%s/%s/%s";

    private WebSocketClient client;

    private SocketClient() {
        EventBus.getDefault().register(this);
    }

    public SocketClient(String matchCode) {
        String realUrl = String.format(urlStr, Api.IP, matchCode, UserData.getInstance().getUser().getDeviceId(), UserData.getInstance().getUser().getAccount());
        URI uri;
        try {
            uri = new URI(realUrl);
            client = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d(TAG, "onOpen()");
                    EventBus.getDefault().post(new EventMessage.Builder(ServiceConstant.SERVICE_TYPE_CONNECT_OPEN).buildEvent());
                }

                @Override
                public void onMessage(String message) {
                    Log.d(TAG, "onMessage()");
                    if ("startconnectyourpartner".equals(message)) {
                        EventMessage.Builder builder = new EventMessage.Builder(ServiceConstant.SERVICE_TYPE_MATCH_SUCCESS);
                        EventBus.getDefault().post(builder.buildEvent());
                        UserData.getInstance().setSocketClient(SocketClient.this);
                    } else {
                        EventMessage.Builder builder = new EventMessage.Builder(ServiceConstant.SERVICE_TYPE_MESSAGE_STRING)
                                .setContent(message);
                        EventBus.getDefault().post(builder.buildEvent());
                    }
                }

                @Override
                public void onMessage(ByteBuffer bytes) {
                    Log.d(TAG, "onMessage()");
                    EventMessage.Builder builder = new EventMessage.Builder(ServiceConstant.SERVICE_TYPE_MESSAGE_BYTE)
                            .setData(bytes);
                    EventBus.getDefault().post(builder.buildEvent());
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d(TAG, "onClose() : " + reason);
                    EventMessage.Builder builder = new EventMessage.Builder(ServiceConstant.SERVICE_TYPE_CONNECT_CLOSE)
                            .setContent(reason);
                    EventBus.getDefault().post(builder.buildEvent());
                }

                @Override
                public void onError(Exception ex) {
                    Log.d(TAG, "onError()");
                    EventBus.getDefault().post(new EventMessage.Builder(ServiceConstant.SERVICE_TYPE_CONNECT_ERROR).buildEvent());
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        client.connect();
    }

    public void sendMessage(String message) {
        if (client.isOpen()) {
            client.send(message);
        }
    }

    public void sendMessage(Object object) {
        byte[] bytes = SerializeUtil.serialize(object);
        if (client.isOpen()) {
            client.send(bytes);
            Log.d(TAG, "send object message");
        }
        Log.d(TAG, "socket is closed");
    }

    public void close(){
        client.close();
    }

    public void destroy() {
        client.close();
        client = null;
        EventBus.getDefault().unregister(this);
    }
}
