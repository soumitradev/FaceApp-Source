package com.pdrogfer.mididroid.event.meta;

import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

public class SmpteOffset extends MetaEvent {
    public static final int FRAME_RATE_24 = 0;
    public static final int FRAME_RATE_25 = 1;
    public static final int FRAME_RATE_30 = 3;
    public static final int FRAME_RATE_30_DROP = 2;
    private FrameRate mFrameRate;
    private int mFrames;
    private int mHours;
    private int mMinutes;
    private int mSeconds;
    private int mSubFrames;

    public enum FrameRate {
        FRAME_RATE_24(0),
        FRAME_RATE_25(1),
        FRAME_RATE_30_DROP(2),
        FRAME_RATE_30(3);
        
        public final int value;

        private FrameRate(int v) {
            this.value = v;
        }

        public static FrameRate fromInt(int val) {
            switch (val) {
                case 0:
                    return FRAME_RATE_24;
                case 1:
                    return FRAME_RATE_25;
                case 2:
                    return FRAME_RATE_30_DROP;
                case 3:
                    return FRAME_RATE_30;
                default:
                    return null;
            }
        }
    }

    public SmpteOffset(long tick, long delta, FrameRate fps, int hour, int min, int sec, int fr, int subfr) {
        super(tick, delta, 84, new VariableLengthInt(5));
        this.mFrameRate = fps;
        this.mHours = hour;
        this.mMinutes = min;
        this.mSeconds = sec;
        this.mFrames = fr;
        this.mSubFrames = subfr;
    }

    public void setFrameRate(FrameRate fps) {
        this.mFrameRate = fps;
    }

    public FrameRate getFrameRate() {
        return this.mFrameRate;
    }

    public void setHours(int h) {
        this.mHours = h;
    }

    public int getHours() {
        return this.mHours;
    }

    public void setMinutes(int m) {
        this.mMinutes = m;
    }

    public int getMinutes() {
        return this.mMinutes;
    }

    public void setSeconds(int s) {
        this.mSeconds = s;
    }

    public int getSeconds() {
        return this.mSeconds;
    }

    public void setFrames(int f) {
        this.mFrames = f;
    }

    public int getFrames() {
        return this.mFrames;
    }

    public void setSubFrames(int s) {
        this.mSubFrames = s;
    }

    public int getSubFrames() {
        return this.mSubFrames;
    }

    protected int getEventSize() {
        return 8;
    }

    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(5);
        out.write(this.mHours);
        out.write(this.mMinutes);
        out.write(this.mSeconds);
        out.write(this.mFrames);
        out.write(this.mSubFrames);
    }

    public static MetaEvent parseSmpteOffset(long tick, long delta, MetaEventData info) {
        MetaEventData metaEventData = info;
        if (metaEventData.length.getValue() != 5) {
            return new GenericMetaEvent(tick, delta, metaEventData);
        }
        int rrHours = metaEventData.data[0];
        return new SmpteOffset(tick, delta, FrameRate.fromInt(rrHours >> 5), rrHours & 31, metaEventData.data[1], metaEventData.data[2], metaEventData.data[3], metaEventData.data[4]);
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
        } else if (other instanceof SmpteOffset) {
            return 0;
        } else {
            return 1;
        }
    }
}
