package com.pdrogfer.mididroid.event.meta;

import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.util.MidiUtil;
import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

public class Tempo extends MetaEvent {
    public static final float DEFAULT_BPM = 120.0f;
    public static final int DEFAULT_MPQN = 500000;
    private float mBPM;
    private int mMPQN;

    public Tempo() {
        this(0, 0, DEFAULT_MPQN);
    }

    public Tempo(long tick, long delta, int mpqn) {
        super(tick, delta, 81, new VariableLengthInt(3));
        setMpqn(mpqn);
    }

    public int getMpqn() {
        return this.mMPQN;
    }

    public float getBpm() {
        return this.mBPM;
    }

    public void setMpqn(int m) {
        this.mMPQN = m;
        this.mBPM = 6.0E7f / ((float) this.mMPQN);
    }

    public void setBpm(float b) {
        this.mBPM = b;
        this.mMPQN = (int) (6.0E7f / this.mBPM);
    }

    protected int getEventSize() {
        return 6;
    }

    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(3);
        out.write(MidiUtil.intToBytes(this.mMPQN, 3));
    }

    public static MetaEvent parseTempo(long tick, long delta, MetaEventData info) {
        if (info.length.getValue() != 3) {
            return new GenericMetaEvent(tick, delta, info);
        }
        return new Tempo(tick, delta, MidiUtil.bytesToInt(info.data, 0, 3));
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
        } else if (!(other instanceof Tempo)) {
            return 1;
        } else {
            Tempo o = (Tempo) other;
            if (this.mMPQN == o.mMPQN) {
                return 0;
            }
            if (this.mMPQN >= o.mMPQN) {
                i = 1;
            }
            return i;
        }
    }
}
