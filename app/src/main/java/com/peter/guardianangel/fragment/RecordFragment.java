package com.peter.guardianangel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.peter.guardianangel.BaseFragment;
import com.peter.guardianangel.R;
import com.peter.guardianangel.data.EventMessage;
import com.peter.guardianangel.data.ServiceConstant;
import com.peter.guardianangel.data.SocketClient;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.util.ToastHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordFragment extends BaseFragment {

    @BindView(R.id.fragment_record_et_message)
    EditText et_message;
    @BindView(R.id.fragment_record_btn_send)
    Button btn_send;

    @Override
    protected void initData() {
        super.initData();
        register();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_record;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        return super.onCreateView(inflater, container, savedInstanceState);
//        return inflater.inflate(R.layout.fragment_record,container,false);
//    }

    @OnClick(R.id.fragment_record_btn_send)
    public void send() {
        SocketClient client = UserData.getInstance().getSocketClient();
        client.sendMessage(et_message.getText().toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventMessage messageEvent) {
        int action = messageEvent.getAction();
        Object data = messageEvent.getData();
        String content = messageEvent.getContent();
        switch (action) {
            case ServiceConstant.SERVICE_TYPE_MESSAGE_STRING:
                ToastHelper.show(getContext(), content);
                break;
            default:
                break;
        }
    }
}
