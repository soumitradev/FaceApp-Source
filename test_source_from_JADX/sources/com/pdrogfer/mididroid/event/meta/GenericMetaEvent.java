package com.pdrogfer.mididroid.event.meta;

import com.pdrogfer.mididroid.event.MidiEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class GenericMetaEvent extends MetaEvent {
    private byte[] mData;

    protected GenericMetaEvent(long tick, long delta, MetaEventData info) {
        super(tick, delta, info.type, info.length);
        this.mData = info.data;
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Warning: GenericMetaEvent used because type (");
        stringBuilder.append(info.type);
        stringBuilder.append(") wasn't recognized or unexpected data length (");
        stringBuilder.append(info.length.getValue());
        stringBuilder.append(") for type.");
        printStream.println(stringBuilder.toString());
    }

    protected int getEventSize() {
        return (this.mLength.getByteCount() + 2) + this.mLength.getValue();
    }

    protected void writeToFile(OutputStream out) throws IOException {
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
        } else if (((long) this.mDelta.getValue()) == other.getDelta()) {
            return 1;
        } else {
            if (((long) this.mDelta.getValue()) < other.getDelta()) {
                i = 1;
            }
            return i;
        }
    }
}
