package com.peter.guardianangel.mvp.contracts.view;

public interface ProtectView {
    void connectionSuccess();

    void connectionOpen();

    void connectionClose(String reason);

    void connectionError();
}
