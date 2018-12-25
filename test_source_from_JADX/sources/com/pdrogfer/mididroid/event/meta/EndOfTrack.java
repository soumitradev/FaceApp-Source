package com.pdrogfer.mididroid.event.meta;

import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

public class EndOfTrack extends MetaEvent {
    public EndOfTrack(long tick, long delta) {
        super(tick, delta, 47, new VariableLengthInt(0));
    }

    protected int getEventSize() {
        return 3;
    }

    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(0);
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
        } else if (other instanceof EndOfTrack) {
            return 0;
        } else {
            return 1;
        }
    }
}
