package com.peter.guardianangel.mvp;

import android.os.Bundle;

import com.peter.guardianangel.BaseActivity;
import com.peter.guardianangel.base.BasePresenter;

public abstract class MvpActivity<T extends BasePresenter> extends BaseActivity {
    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        checkNotNull(presenter).detachView();
        if (presenter != null){
            presenter.detachView();
        }
    }
}