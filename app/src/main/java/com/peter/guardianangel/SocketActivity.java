package com.peter.guardianangel;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.peter.guardianangel.adapter.MessageAdapter;
import com.peter.guardianangel.data.UserData;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Peter
 */
public class SocketActivity extends AppCompatActivity {
    EditText et_intpu;
    EditText et_message;
    Button btn_send;
    Button btn_close;
    Button btn_connect;

//    String urlStr = "ws://106.15.92.137:8080/app/websocket";
    String urlStr = "ws://192.168.31.174:8080/mywebsocket/%s/%s";
    WebSocketClient mainWebSocketClient;
    Handler handler;

    String macthCode;

    List<String> mDatas = new ArrayList<>();
    MessageAdapter adapter;

    RecyclerView rv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        et_intpu = findViewById(R.id.et_input);
        et_message = findViewById(R.id.et_message);
        btn_send = findViewById(R.id.btn_send);
        btn_close = findViewById(R.id.btn_close);
        btn_connect = findViewById(R.id.btn_connect);
        rv_message = findViewById(R.id.rv_message);

        macthCode = getIntent().getStringExtra("matchcode");

        handler = new Handler(Looper.getMainLooper());

//        initConfig();
        initRecyclerview();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainWebSocketClient.send(et_message.getText().toString());
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainWebSocketClient.close(CloseFrame.NORMAL, "test close");
            }
        });

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initConfig();
                mainWebSocketClient.connect();
            }
        });
    }

    private void initRecyclerview() {
        adapter = new MessageAdapter(this, mDatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_message.setLayoutManager(layoutManager);
        rv_message.setAdapter(adapter);
    }

    private void initConfig() {
        longConnectConfig();
    }

    private void longConnectConfig(){
        try {
            String realUrl = String.format(urlStr, et_intpu.getText().toString(), UserData.getInstance().getDeviceId());
            URI uri = new URI(realUrl);
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
                    mDatas.add(message);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getDeviceId() {
        String serial = "";
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return szDevIDShort;
        }
        String devid = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if(devid != null) {
            szDevIDShort = szDevIDShort + serial + devid;
        }else {
            szDevIDShort = szDevIDShort + serial;
        }

        return szDevIDShort;
    }
}
