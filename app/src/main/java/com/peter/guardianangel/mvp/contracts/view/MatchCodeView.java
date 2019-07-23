package com.peter.guardianangel.mvp.contracts.view;

import com.peter.guardianangel.base.BaseView;

public interface MatchCodeView extends BaseView {
    void updateMatchCode(String matchCode);

    void connectionOpen();

    void receiveMessage(String message);

    void connectionClose(String reason);

    void connectionError();

    void errorMatchCode();

    void jumpMainPage();
}
