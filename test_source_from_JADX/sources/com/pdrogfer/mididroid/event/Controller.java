package com.pdrogfer.mididroid.event;

public class Controller extends ChannelEvent {
    public Controller(long tick, int channel, int controllerType, int value) {
        super(tick, 11, channel, controllerType, value);
    }

    public Controller(long tick, long delta, int channel, int controllerType, int value) {
        super(tick, delta, 11, channel, controllerType, value);
    }

    public int getControllerType() {
        return this.mValue1;
    }

    public int getValue() {
        return this.mValue2;
    }

    public void setControllerType(int t) {
        this.mValue1 = t;
    }

    public void setValue(int v) {
        this.mValue2 = v;
    }
}
