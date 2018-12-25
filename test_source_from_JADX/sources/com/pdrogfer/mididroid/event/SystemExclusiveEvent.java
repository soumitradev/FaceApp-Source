package com.pdrogfer.mididroid.event;

import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;

public class SystemExclusiveEvent extends MidiEvent {
    private byte[] mData;
    private VariableLengthInt mLength;
    private int mType;

    public SystemExclusiveEvent(int type, long tick, byte[] data) {
        this(type, tick, 0, data);
    }

    public SystemExclusiveEvent(int type, long tick, long delta, byte[] data) {
        super(tick, delta);
        this.mType = type & 255;
        if (!(this.mType == SysexMessageWriter.COMMAND_START || this.mType == 247)) {
            this.mType = SysexMessageWriter.COMMAND_START;
        }
        this.mLength = new VariableLengthInt(data.length);
        this.mData = data;
    }

    public byte[] getData() {
        return this.mData;
    }

    public void setData(byte[] data) {
        this.mLength.setValue(data.length);
        this.mData = data;
    }

    public boolean requiresStatusByte(MidiEvent prevEvent) {
        return true;
    }

    public void writeToFile(OutputStream out, boolean writeType) throws IOException {
        super.writeToFile(out, writeType);
        out.write(this.mType);
        out.write(this.mLength.getBytes());
        out.write(this.mData);
    }

    public int compareTo(MidiEvent other) {
        if (this.mTick < other.mTick) {
            return -1;
        }
        if (this.mTick > other.mTick) {
            return 1;
        }
        if (this.mDelta.getValue() > other.mDelta.getValue()) {
            return -1;
        }
        if (this.mDelta.getValue() >= other.mDelta.getValue() && (other instanceof SystemExclusiveEvent)) {
            return new String(this.mData).compareTo(new String(((SystemExclusiveEvent) other).mData));
        }
        return 1;
    }

    protected int getEventSize() {
        return (this.mLength.getByteCount() + 1) + this.mData.length;
    }
}
