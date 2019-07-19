package com.peter.guardianangel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.peter.guardianangel.R;
import com.peter.guardianangel.SocketActivity;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.MatchCodePresenter;
import com.peter.guardianangel.mvp.contracts.view.MatchCodeView;

import butterknife.BindView;
import butterknife.OnClick;

public class MatchCodeActivity extends MvpActivity<MatchCodePresenter> implements MatchCodeView {

    @BindView(R.id.activity_match_code_tv_code)
    TextView tv_code;
//    @BindView(R.id.activity_match_code_btn_jump)
//    Button btn_jump;

    @Override
    protected void initData() {
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

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

//    @OnClick(R.id.activity_match_code_btn_jump)
//    public void jump () {
//        Intent intent = new Intent(MatchCodeActivity.this, SocketActivity.class);
//        intent.putExtra("matchcode", tv_code.getText().toString());
//        startActivity(intent);
//    }

    @Override
    public void updateMatchCode(String matchCode) {
        tv_code.setText(matchCode);
    }
}
