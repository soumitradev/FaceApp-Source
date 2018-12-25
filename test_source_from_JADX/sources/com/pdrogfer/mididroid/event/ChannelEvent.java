package com.pdrogfer.mididroid.event;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class ChannelEvent extends MidiEvent {
    public static final int CHANNEL_AFTERTOUCH = 13;
    public static final int CONTROLLER = 11;
    public static final int NOTE_AFTERTOUCH = 10;
    public static final int NOTE_OFF = 8;
    public static final int NOTE_ON = 9;
    public static final int PITCH_BEND = 14;
    public static final int PROGRAM_CHANGE = 12;
    private static HashMap<Integer, Integer> mOrderMap;
    protected int mChannel;
    protected int mType;
    protected int mValue1;
    protected int mValue2;

    protected ChannelEvent(long tick, int type, int channel, int param1, int param2) {
        this(tick, 0, type, channel, param1, param2);
    }

    protected ChannelEvent(long tick, long delta, int type, int channel, int param1, int param2) {
        super(tick, delta);
        this.mType = type & 15;
        this.mChannel = channel & 15;
        this.mValue1 = param1 & 255;
        this.mValue2 = param2 & 255;
    }

    public int getType() {
        return this.mType;
    }

    public void setChannel(int c) {
        if (c < 0) {
            c = 0;
        } else if (c > 15) {
            c = 15;
        }
        this.mChannel = c;
    }

    public int getChannel() {
        return this.mChannel;
    }

    protected int getEventSize() {
        switch (this.mType) {
            case 12:
            case 13:
                return 2;
            default:
                return 3;
        }
    }

    public int compareTo(MidiEvent other) {
        int i = -1;
        if (this.mTick != other.getTick()) {
            if (this.mTick >= other.getTick()) {
                i = 1;
            }
            return i;
        } else if (this.mDelta.getValue() != other.mDelta.getValue()) {
            if (this.mDelta.getValue() < other.mDelta.getValue()) {
                i = 1;
            }
            return i;
        } else if (!(other instanceof ChannelEvent)) {
            return 1;
        } else {
            ChannelEvent o = (ChannelEvent) other;
            if (this.mType != o.getType()) {
                if (mOrderMap == null) {
                    buildOrderMap();
                }
                if (((Integer) mOrderMap.get(Integer.valueOf(this.mType))).intValue() >= ((Integer) mOrderMap.get(Integer.valueOf(o.getType()))).intValue()) {
                    i = 1;
                }
                return i;
            } else if (this.mValue1 != o.mValue1) {
                if (this.mValue1 >= o.mValue1) {
                    i = 1;
                }
                return i;
            } else if (this.mValue2 != o.mValue2) {
                if (this.mValue2 >= o.mValue2) {
                    i = 1;
                }
                return i;
            } else if (this.mChannel == o.getChannel()) {
                return 0;
            } else {
                if (this.mChannel >= o.getChannel()) {
                    i = 1;
                }
                return i;
            }
        }
    }

    public boolean requiresStatusByte(MidiEvent prevEvent) {
        boolean z = true;
        if (prevEvent == null || !(prevEvent instanceof ChannelEvent)) {
            return true;
        }
        ChannelEvent ce = (ChannelEvent) prevEvent;
        if (this.mType == ce.getType()) {
            if (this.mChannel == ce.getChannel()) {
                z = false;
            }
        }
        return z;
    }

    public void writeToFile(OutputStream out, boolean writeType) throws IOException {
        super.writeToFile(out, writeType);
        if (writeType) {
            out.write((this.mType << 4) + this.mChannel);
        }
        out.write(this.mValue1);
        if (this.mType != 12 && this.mType != 13) {
            out.write(this.mValue2);
        }
    }

    public static ChannelEvent parseChannelEvent(long tick, long delta, int type, int channel, InputStream in) throws IOException {
        int i = type;
        int val1 = in.read();
        int val2 = 0;
        if (!(i == 12 || i == 13)) {
            val2 = in.read();
        }
        int val22 = val2;
        switch (i) {
            case 8:
                return new NoteOff(tick, delta, channel, val1, val22);
            case 9:
                return new NoteOn(tick, delta, channel, val1, val22);
            case 10:
                return new NoteAftertouch(tick, delta, channel, val1, val22);
            case 11:
                return new Controller(tick, delta, channel, val1, val22);
            case 12:
                return new ProgramChange(tick, delta, channel, val1);
            case 13:
                return new ChannelAftertouch(tick, delta, channel, val1);
            case 14:
                return new PitchBend(tick, delta, channel, val1, val22);
            default:
                return new ChannelEvent(tick, delta, i, channel, val1, val22);
        }
    }

    private static void buildOrderMap() {
        mOrderMap = new HashMap();
        mOrderMap.put(Integer.valueOf(12), Integer.valueOf(0));
        mOrderMap.put(Integer.valueOf(11), Integer.valueOf(1));
        mOrderMap.put(Integer.valueOf(9), Integer.valueOf(2));
        mOrderMap.put(Integer.valueOf(8), Integer.valueOf(3));
        mOrderMap.put(Integer.valueOf(10), Integer.valueOf(4));
        mOrderMap.put(Integer.valueOf(13), Integer.valueOf(5));
        mOrderMap.put(Integer.valueOf(14), Integer.valueOf(6));
    }
}
