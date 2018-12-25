package com.pdrogfer.mididroid.event;

public class NoteAftertouch extends ChannelEvent {
    public NoteAftertouch(long tick, int channel, int note, int amount) {
        super(tick, 10, channel, note, amount);
    }

    public NoteAftertouch(long tick, long delta, int channel, int note, int amount) {
        super(tick, delta, 10, channel, note, amount);
    }

    public int getNoteValue() {
        return this.mValue1;
    }

    public int getAmount() {
        return this.mValue2;
    }

    public void setNoteValue(int p) {
        this.mValue1 = p;
    }

    public void setAmount(int a) {
        this.mValue2 = a;
    }
}
