package com.pdrogfer.mididroid.event;

public class NoteOn extends ChannelEvent {
    public NoteOn(long tick, int channel, int note, int velocity) {
        super(tick, 9, channel, note, velocity);
    }

    public NoteOn(long tick, long delta, int channel, int note, int velocity) {
        super(tick, delta, 9, channel, note, velocity);
    }

    public int getNoteValue() {
        return this.mValue1;
    }

    public int getVelocity() {
        return this.mValue2;
    }

    public void setNoteValue(int p) {
        this.mValue1 = p;
    }

    public void setVelocity(int v) {
        this.mValue2 = v;
    }
}
