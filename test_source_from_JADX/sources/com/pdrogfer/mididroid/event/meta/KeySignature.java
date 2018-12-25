package com.pdrogfer.mididroid.event.meta;

import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

public class KeySignature extends MetaEvent {
    public static final int SCALE_MAJOR = 0;
    public static final int SCALE_MINOR = 1;
    private int mKey;
    private int mScale;

    public KeySignature(long tick, long delta, int key, int scale) {
        super(tick, delta, 89, new VariableLengthInt(2));
        setKey(key);
        this.mScale = scale;
    }

    public void setKey(int key) {
        this.mKey = (byte) key;
        if (this.mKey < -7) {
            this.mKey = -7;
        } else if (this.mKey > 7) {
            this.mKey = 7;
        }
    }

    public int getKey() {
        return this.mKey;
    }

    public void setScale(int scale) {
        this.mScale = scale;
    }

    public int getScale() {
        return this.mScale;
    }

    protected int getEventSize() {
        return 5;
    }

    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(2);
        out.write(this.mKey);
        out.write(this.mScale);
    }

    public static MetaEvent parseKeySignature(long tick, long delta, MetaEventData info) {
        if (info.length.getValue() != 2) {
            return new GenericMetaEvent(tick, delta, info);
        }
        return new KeySignature(tick, delta, info.data[0], info.data[1]);
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
        } else if (!(other instanceof KeySignature)) {
            return 1;
        } else {
            KeySignature o = (KeySignature) other;
            if (this.mKey != o.mKey) {
                if (this.mKey >= o.mKey) {
                    i = 1;
                }
                return i;
            } else if (this.mScale == o.mScale) {
                return 0;
            } else {
                if (this.mKey >= o.mScale) {
                    i = 1;
                }
                return i;
            }
        }
    }
}
