package com.peter.guardianangel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.peter.guardianangel.MainActivity;
import com.peter.guardianangel.R;
import com.peter.guardianangel.SocketActivity;
import com.peter.guardianangel.data.WebSocketConnect;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.MatchCodePresenter;
import com.peter.guardianangel.mvp.contracts.view.MatchCodeView;

import butterknife.BindView;
import butterknife.OnClick;

public class MatchCodeActivity extends MvpActivity<MatchCodePresenter> implements MatchCodeView {

    @BindView(R.id.activity_match_code_tv_title)
    TextView tv_title;
    @BindView(R.id.activity_match_code_tv_code)
    TextView tv_code;
    @BindView(R.id.activity_match_code_et_matchcode)
    EditText et_matchcode;

    private Handler mHandler;

    @Override
    protected void initData() {
        mHandler = new Handler(getMainLooper());

        presenter.getMatchCode();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_match_code;
    }

    @Override
    protected MatchCodePresenter createPresenter() {
        return new MatchCodePresenter(this);
    }

    @OnClick(R.id.fragment_user_tv_bind)
    public void connect(){
        presenter.checkMatchCode(et_matchcode.getText().toString());
    }

    @OnClick(R.id.activity_match_code_tv_title)
    public void title(){
//        WebSocketConnect.getInstance().getWebSocketClient().
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void updateMatchCode(String matchCode) {
        tv_code.setText(matchCode);
    }

    @Override
    public void connectionOpen() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "连接建立", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void receiveMessage(final String message) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "收到消息: " + message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void connectionClose(final String reason) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "连接关闭" + reason, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void connectionError() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "连接错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void errorMatchCode() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "匹配码不存在", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void jumpMainPage() {
        startActivity(new Intent(MatchCodeActivity.this, ProtectActivity.class));
    }
}
