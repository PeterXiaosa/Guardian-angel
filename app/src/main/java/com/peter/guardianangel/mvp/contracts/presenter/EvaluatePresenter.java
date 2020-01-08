package com.peter.guardianangel.mvp.contracts.presenter;

import android.support.annotation.NonNull;

import com.peter.guardianangel.base.BasePresenter;
import com.peter.guardianangel.mvp.contracts.view.EvaluateView;

public class EvaluatePresenter extends BasePresenter<EvaluateView> {

    public EvaluatePresenter(@NonNull EvaluateView view){
        attachView(view);
        initLoginData();
    }

    private void initLoginData(){

    }


}
