package com.peter.guardianangel.mvp.contracts.view;

public interface EvaluateView {
    void evaluateSuccess();

    void evaluateFail(String errorText);
}
