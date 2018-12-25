package com.pdrogfer.mididroid.event.meta;

import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

public class MidiChannelPrefix extends MetaEvent {
    private int mChannel;

    public MidiChannelPrefix(long tick, long delta, int channel) {
        super(tick, delta, 32, new VariableLengthInt(4));
        this.mChannel = channel;
    }

    public void setChannel(int c) {
        this.mChannel = c;
    }

    public int getChannel() {
        return this.mChannel;
    }

    protected int getEventSize() {
        return 4;
    }

    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(1);
        out.write(this.mChannel);
    }

    public static MetaEvent parseMidiChannelPrefix(long tick, long delta, MetaEventData info) {
        if (info.length.getValue() != 1) {
            return new GenericMetaEvent(tick, delta, info);
        }
        return new MidiChannelPrefix(tick, delta, info.data[0]);
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
        } else if (!(other instanceof MidiChannelPrefix)) {
            return 1;
        } else {
            MidiChannelPrefix o = (MidiChannelPrefix) other;
            if (this.mChannel == o.mChannel) {
                return 0;
            }
            if (this.mChannel >= o.mChannel) {
                i = 1;
            }
            return i;
        }
    }
}
