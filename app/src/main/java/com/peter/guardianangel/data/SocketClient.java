package com.peter.guardianangel.data;

import com.peter.guardianangel.retrofit.Api;

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

    private static String urlStr = "ws://%s:8080/JavaWeb_war_exploded/mywebsocket/%s/%s/%s";

    private WebSocketClient client;

    private SocketClient() {
        EventBus.getDefault().register(this);
    }

    public SocketClient(String matchCode) {
        String realUrl = String.format(urlStr, Api.IP, matchCode, UserData.getInstance().getUser().getDeviceId(), UserData.getInstance().getUser().getAccount());
        URI uri = null;
        try {
            uri = new URI(realUrl);
            client = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    EventBus.getDefault().post(new EventMessage.Builder(ServiceConstant.SERVICE_TYPE_CONNECT_OPEN).buildEvent());
                }

                @Override
                public void onMessage(String message) {
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
                    EventMessage.Builder builder = new EventMessage.Builder(ServiceConstant.SERVICE_TYPE_MESSAGE_BYTE)
                            .setData(bytes);
                    EventBus.getDefault().post(builder.buildEvent());
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    EventMessage.Builder builder = new EventMessage.Builder(ServiceConstant.SERVICE_TYPE_CONNECT_CLOSE)
                            .setContent(reason);
                    EventBus.getDefault().post(builder.buildEvent());
                }

                @Override
                public void onError(Exception ex) {
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
        client.send(message);
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
