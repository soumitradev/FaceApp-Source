package com.pdrogfer.mididroid.event;

import com.pdrogfer.mididroid.event.meta.MetaEvent;

public class PitchBend extends ChannelEvent {
    public PitchBend(long tick, int channel, int lsb, int msb) {
        super(tick, 14, channel, lsb, msb);
    }

    public PitchBend(long tick, long delta, int channel, int lsb, int msb) {
        super(tick, delta, 14, channel, lsb, msb);
    }

    public int getLeastSignificantBits() {
        return this.mValue1;
    }

    public int getMostSignificantBits() {
        return this.mValue2;
    }

    public int getBendAmount() {
        return ((this.mValue2 & MetaEvent.SEQUENCER_SPECIFIC) << 7) + this.mValue1;
    }

    public void setLeastSignificantBits(int p) {
        this.mValue1 = p & MetaEvent.SEQUENCER_SPECIFIC;
    }

    public void setMostSignificantBits(int p) {
        this.mValue2 = p & MetaEvent.SEQUENCER_SPECIFIC;
    }

    public void setBendAmount(int amount) {
        amount &= 16383;
        this.mValue1 = amount & MetaEvent.SEQUENCER_SPECIFIC;
        this.mValue2 = amount >> 7;
    }
}
