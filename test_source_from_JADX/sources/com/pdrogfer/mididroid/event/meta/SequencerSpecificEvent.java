package com.pdrogfer.mididroid.event.meta;

import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.util.MidiUtil;
import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

public class SequencerSpecificEvent extends MetaEvent {
    private byte[] mData;

    public SequencerSpecificEvent(long tick, long delta, byte[] data) {
        super(tick, delta, MetaEvent.SEQUENCER_SPECIFIC, new VariableLengthInt(data.length));
        this.mData = data;
    }

    public void setData(byte[] data) {
        this.mData = data;
        this.mLength.setValue(this.mData.length);
    }

    public byte[] getData() {
        return this.mData;
    }

    protected int getEventSize() {
        return (this.mLength.getByteCount() + 2) + this.mData.length;
    }

    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(this.mLength.getBytes());
        out.write(this.mData);
    }

    public int compareTo(MidiEvent other) {
        int i = -1;
        if (this.mTick != other.getTick()) {
            if (this.mTick >= other.getTick()) {
                i = 1;
            }
            return i;
        } else if (((long) this.mDelta.getValue()) != other.getDelta()) {
            if (((long) this.mDelta.getValue()) < other.getDelta()) {
                i = 1;
            }
            return i;
        } else if (!(other instanceof SequencerSpecificEvent)) {
            return 1;
        } else {
            if (MidiUtil.bytesEqual(this.mData, ((SequencerSpecificEvent) other).mData, 0, this.mData.length)) {
                return 0;
            }
            return 1;
        }
    }
}
