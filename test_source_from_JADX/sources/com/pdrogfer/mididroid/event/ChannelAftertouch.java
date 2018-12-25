package com.pdrogfer.mididroid.event;

public class ChannelAftertouch extends ChannelEvent {
    public ChannelAftertouch(long tick, int channel, int amount) {
        super(tick, 13, channel, amount, 0);
    }

    public ChannelAftertouch(long tick, long delta, int channel, int amount) {
        super(tick, delta, 13, channel, amount, 0);
    }

    public int getAmount() {
        return this.mValue1;
    }

    public void setAmount(int p) {
        this.mValue1 = p;
    }
}
