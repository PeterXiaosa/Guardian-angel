package com.peter.guardianangel;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.http.WebSocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Peter
 */
public class SocketActivity extends AppCompatActivity {

    EditText et_intpu;
    Button btn_send;
    Button btn_close;
    Button btn_connect;

//    String urlStr = "ws://106.15.92.137:8080/app/websocket";
    String urlStr = "ws://10.0.2.2:8080/mywebsocket/abcd123/newparams";
    WebSocketClient mainWebSocketClient;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        et_intpu = findViewById(R.id.et_input);
        btn_send = findViewById(R.id.btn_send);
        btn_close = findViewById(R.id.btn_close);
        btn_connect = findViewById(R.id.btn_connect);

        handler = new Handler(Looper.getMainLooper());

        connect();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainWebSocketClient.send(et_intpu.getText().toString());
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mainWebSocketClient.close();
                mainWebSocketClient.close(CloseFrame.NORMAL, "test close");
            }
        });

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainWebSocketClient.connect();
            }
        });
    }

    private void connect() {

//        httpConnect();

        longConnect();
    }

    private void httpConnect() {
        String url = "http://10.0.2.2:8080/untitled/getJson";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    Log.d("peterokhttp", "run: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void longConnect(){

        try {
            URI uri = new URI(urlStr);
            mainWebSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("peterfu", "连接建立");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "连接建立", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onMessage(final String message) {
                    Log.d("peterfu", "收到消息");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "收到消息: " + message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("peterfu", "连接关闭");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "连接关闭", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(Exception ex) {
                    Log.d("peterfu", "连接错误");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "发生错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            };
            mainWebSocketClient.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
