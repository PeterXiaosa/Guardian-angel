package com.peter.guardianangel.data;

import org.java_websocket.client.WebSocketClient;

public class WebSocketConnect {

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

}
