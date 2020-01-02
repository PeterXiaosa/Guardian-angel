package com.peter.guardianangel.data;

public class EventMessage extends AbstractEvent {
    protected Builder builder;

    public EventMessage(Builder builder) {
        this.builder = builder;
    }

    @Deprecated
    public EventMessage(int sender, int action) {
        builder = new Builder(action) {
            @Override
            public EventMessage buildEvent() {
                return new EventMessage(this);
            }
        };
        builder.setSender(sender);
    }

    @Deprecated
    public EventMessage(int sender, int action, String content) {
        builder = new Builder(action) {
            @Override
            public EventMessage buildEvent() {
                return new EventMessage(this);
            }
        };
        builder.setSender(sender);
        builder.setContent(content);
    }

    @Deprecated
    public EventMessage(int sender, int action, Object data) {
        builder = new Builder(action) {
            @Override
            public EventMessage buildEvent() {
                return new EventMessage(this);
            }
        };
        builder.setSender(sender);
        builder.setData(data);
    }

    @Deprecated
    public EventMessage(int sender, int action, String content, Object data) {
        builder = new Builder(action) {
            @Override
            public EventMessage buildEvent() {
                return new EventMessage(this);
            }
        };
        builder.setSender(sender);
        builder.setData(data);
        builder.setContent(content);
    }

    public static class Builder extends AbstractEventBuilder<EventMessage> {

        String content;

        public Builder(int action) {
            setAction(action);
        }

        @Override
        public EventMessage.Builder setSender(int sender) {
            return (EventMessage.Builder) super.setSender(sender);
        }

        @Override
        public EventMessage.Builder setAction(int action) {
            return (EventMessage.Builder) super.setAction(action);
        }

        @Override
        public EventMessage.Builder setData(Object data) {
            return (EventMessage.Builder) super.setData(data);
        }

        public EventMessage.Builder setContent(String content) {
            this.content = content;
            return this;
        }

        @Override
        public EventMessage buildEvent() {
            return new EventMessage(this);
        }
    }

    public int getSender() {
        return builder.mSender;
    }

    public int getAction() {
        return builder.mAction;
    }

    public Object getData() {
        return builder.mData;
    }

    public String getContent() {
        return builder.content;
    }

}
