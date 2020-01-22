package com.peter.guardianangel.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.peter.guardianangel.R;
import com.peter.guardianangel.bean.ChildrenData;
import com.peter.guardianangel.bean.Evaluate;
import com.peter.guardianangel.bean.User;
import com.peter.guardianangel.data.UserData;
import com.peter.guardianangel.mvp.MvpActivity;
import com.peter.guardianangel.mvp.contracts.presenter.EvaluatePresenter;
import com.peter.guardianangel.mvp.contracts.view.EvaluateView;
import com.peter.guardianangel.util.ToastHelper;
import com.peter.guardianangel.view.MyToolbar;
import com.peter.guardianangel.view.starRatingBar.CustomRatingBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class EvaluateActivity extends MvpActivity<EvaluatePresenter> implements EvaluateView {

    @BindView(R.id.activity_evaluate_et_content)
    EditText et_content;
    @BindView(R.id.activity_evaluate_tv_text_num)
    TextView tv_text_num;
    @BindView(R.id.activity_evaluate_btn_submit)
    Button btn_submit;
    @BindView(R.id.activity_evaluate_tv_text_maxnum)
    TextView tv_maxnum;
    @BindView(R.id.activity_evaluate_toolbar)
    MyToolbar toolbar;
    @BindView(R.id.activity_about_crb_judge)
    CustomRatingBar customRatingBar;

    private final int maxNum = 200;

    ChildrenData childrenData;

    @Override
    protected void initData() {
        super.initData();

        childrenData = getIntent().getParcelableExtra("data");
    }

    @Override
    protected void initView() {
        super.initView();
        tv_maxnum.setText(String.valueOf(maxNum));

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_text_num.setText(String.valueOf(s.length()));
                if (s.length() > maxNum) {
                    tv_text_num.setTextColor(getResources().getColor(R.color.red, null));
                } else {
                    tv_text_num.setTextColor(getResources().getColor(R.color.black, null));
                }
            }
        });

        toolbar.setToolbarClick(new MyToolbar.IToolbarClick() {
            @Override
            public void clickIcon(boolean isLeft, int position) {
                if (isLeft && position == 1) {
                    finish();
                }
            }
        });
    }

    @OnClick(R.id.activity_evaluate_btn_submit)
    public void submit() {
        if (et_content.getText().toString().length() > maxNum) {
            ToastHelper.show(getApplicationContext(), "不要写小作文啦，字数已超过最大限制");
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Evaluate evaluate = new Evaluate();
        User user = UserData.getInstance().getUser();
        evaluate.setEvaluateAccount(user.getAccount());
        evaluate.setBeEvaluatedAccount(user.getPartnerAccount());
        evaluate.setContent(et_content.getText().toString());
        evaluate.setStarRank((int)(customRatingBar.getRating() * 2));
        evaluate.setEvaluateDate(dateFormat.format(new Date()));

        String month = formatData(childrenData.getMonth());
        String startDay = formatData(childrenData.getStart());
        String endDay = formatData(childrenData.getEnd());
        evaluate.setEvaluateStart(String.format("2020-%s-%s", month, startDay));
        evaluate.setEvaluateEnd(String.format("2020-%s-%s", month, endDay));
        presenter.addEvaluate(evaluate);
    }

    public String formatData(int num) {
        if (num <10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected EvaluatePresenter createPresenter() {
        return new EvaluatePresenter(this);
    }

    @Override
    public void evaluateSuccess() {
        ToastHelper.show(this, "评论成功");
    }

    @Override
    public void evaluateFail(String errorText) {
        ToastHelper.show(this, "评论失败 : " + errorText);
    }
}
