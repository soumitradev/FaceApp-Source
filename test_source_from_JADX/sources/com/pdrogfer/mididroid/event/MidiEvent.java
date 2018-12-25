package com.pdrogfer.mididroid.event;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;

public abstract class MidiEvent implements Comparable<MidiEvent> {
    private static int sChannel = -1;
    private static int sId = -1;
    private static int sType = -1;
    protected VariableLengthInt mDelta;
    protected long mTick;

    protected abstract int getEventSize();

    public MidiEvent(long tick, long delta) {
        this.mTick = tick;
        this.mDelta = new VariableLengthInt((int) delta);
    }

    public long getTick() {
        return this.mTick;
    }

    public long getDelta() {
        return (long) this.mDelta.getValue();
    }

    public void setDelta(long d) {
        this.mDelta.setValue((int) d);
    }

    public int getSize() {
        return getEventSize() + this.mDelta.getByteCount();
    }

    public boolean requiresStatusByte(MidiEvent prevEvent) {
        if (prevEvent == null || (this instanceof MetaEvent) || !getClass().equals(prevEvent.getClass())) {
            return true;
        }
        return false;
    }

    public void writeToFile(OutputStream out, boolean writeType) throws IOException {
        out.write(this.mDelta.getBytes());
    }

    public static final MidiEvent parseEvent(long tick, long delta, InputStream in) throws IOException {
        InputStream inputStream = in;
        inputStream.mark(1);
        boolean reset = false;
        if (!verifyIdentifier(in.read())) {
            in.reset();
            reset = true;
        }
        boolean reset2 = reset;
        if (sType >= 8 && sType <= 14) {
            return ChannelEvent.parseChannelEvent(tick, delta, sType, sChannel, inputStream);
        } else if (sId == 255) {
            return MetaEvent.parseMetaEvent(tick, delta, in);
        } else {
            if (sId != SysexMessageWriter.COMMAND_START) {
                if (sId != 247) {
                    PrintStream printStream = System.out;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to handle status byte, skipping: ");
                    stringBuilder.append(sId);
                    printStream.println(stringBuilder.toString());
                    if (reset2) {
                        in.read();
                    }
                    return null;
                }
            }
            byte[] data = new byte[new VariableLengthInt(inputStream).getValue()];
            inputStream.read(data);
            return new SystemExclusiveEvent(sId, tick, delta, data);
        }
    }

    private static boolean verifyIdentifier(int id) {
        sId = id;
        int type = id >> 4;
        int channel = id & 15;
        if (type >= 8 && type <= 14) {
            sId = id;
            sType = type;
            sChannel = channel;
        } else if (id == 255) {
            sId = id;
            sType = -1;
            sChannel = -1;
        } else if (type != 15) {
            return false;
        } else {
            sId = id;
            sType = type;
            sChannel = -1;
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.mTick);
        stringBuilder.append(" (");
        stringBuilder.append(this.mDelta.getValue());
        stringBuilder.append("): ");
        stringBuilder.append(getClass().getSimpleName());
        return stringBuilder.toString();
    }
}
