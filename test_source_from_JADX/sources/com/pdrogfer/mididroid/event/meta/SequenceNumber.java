package com.pdrogfer.mididroid.event.meta;

import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

public class SequenceNumber extends MetaEvent {
    private int mNumber;

    public SequenceNumber(long tick, long delta, int number) {
        super(tick, delta, 0, new VariableLengthInt(2));
        this.mNumber = number;
    }

    public int getMostSignificantBits() {
        return this.mNumber >> 8;
    }

    public int getLeastSignificantBits() {
        return this.mNumber & 255;
    }

    public int getSequenceNumber() {
        return this.mNumber;
    }

    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(2);
        out.write(getMostSignificantBits());
        out.write(getLeastSignificantBits());
    }

    public static MetaEvent parseSequenceNumber(long tick, long delta, MetaEventData info) {
        if (info.length.getValue() != 2) {
            return new GenericMetaEvent(tick, delta, info);
        }
        return new SequenceNumber(tick, delta, (info.data[0] << 8) + info.data[1]);
    }

    protected int getEventSize() {
        return 5;
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
        } else if (!(other instanceof SequenceNumber)) {
            return 1;
        } else {
            SequenceNumber o = (SequenceNumber) other;
            if (this.mNumber == o.mNumber) {
                return 0;
            }
            if (this.mNumber >= o.mNumber) {
                i = 1;
            }
            return i;
        }
    }
}
