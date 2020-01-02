package com.peter.guardianangel.data;

public abstract class AbstractEvent {
    protected AbstractEventBuilder Builder;

    AbstractEvent(){}

    public AbstractEvent(AbstractEventBuilder builder){
        Builder = builder;
    }

    public int getSender() {
        return Builder.mSender;
    }

    public int getAction() {
        return Builder.mAction;
    }

    public Object getData() {
        return Builder.mData;
    }
}
