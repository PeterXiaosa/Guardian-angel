package com.peter.guardianangel.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.peter.guardianangel.R;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.EvaluatePresenter;
import com.peter.guardianangel.mvp.contracts.view.EvaluateView;

import butterknife.BindView;

public class EvaluateActivity extends MvpActivity<EvaluatePresenter> implements EvaluateView {

    @BindView(R.id.activity_evaluate_et_content)
    EditText et_content;
    @BindView(R.id.activity_evaluate_tv_text_num)
    TextView tv_text_num;
    @BindView(R.id.activity_evaluate_btn_submit)
    Button btn_submit;

    private final int maxNum = 200;

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_text_num.setText(s.length() + " / " + maxNum);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected EvaluatePresenter createPresenter() {
        return new EvaluatePresenter(this);
    }
}
