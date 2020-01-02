package com.peter.guardianangel.data;

public abstract class AbstractEventBuilder<T extends AbstractEvent> {
    int mSender;
    int mAction;
    Object mData;

    public AbstractEventBuilder setSender(int sender) {
        this.mSender = sender;
        return this;
    }

    public AbstractEventBuilder setAction(int action) {
        this.mAction = action;
        return this;
    }

    public AbstractEventBuilder setData(Object data) {
        this.mData = data;
        return this;
    }

    abstract public T buildEvent();
}
