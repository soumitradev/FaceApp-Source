package com.pdrogfer.mididroid;

import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.event.NoteOff;
import com.pdrogfer.mididroid.event.NoteOn;
import com.pdrogfer.mididroid.event.meta.EndOfTrack;
import com.pdrogfer.mididroid.event.meta.Tempo;
import com.pdrogfer.mididroid.event.meta.TimeSignature;
import com.pdrogfer.mididroid.util.MidiUtil;
import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.TreeSet;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class MidiTrack {
    public static final byte[] IDENTIFIER = new byte[]{GeneralMidiConstants.SHAKUHACHI, GeneralMidiConstants.LEAD_4_CHARANG, GeneralMidiConstants.STEEL_DRUMS, GeneralMidiConstants.KOTO};
    private static final boolean VERBOSE = false;
    private boolean mClosed;
    private long mEndOfTrackDelta;
    private TreeSet<MidiEvent> mEvents;
    private int mSize;
    private boolean mSizeNeedsRecalculating;

    public static MidiTrack createTempoTrack() {
        MidiTrack T = new MidiTrack();
        T.insertEvent(new TimeSignature());
        T.insertEvent(new Tempo());
        return T;
    }

    public MidiTrack() {
        this.mEvents = new TreeSet();
        this.mSize = 0;
        this.mSizeNeedsRecalculating = false;
        this.mClosed = false;
        this.mEndOfTrackDelta = 0;
    }

    public MidiTrack(InputStream in) throws IOException {
        this();
        byte[] buffer = new byte[4];
        in.read(buffer);
        if (MidiUtil.bytesEqual(buffer, IDENTIFIER, 0, 4)) {
            in.read(buffer);
            this.mSize = MidiUtil.bytesToInt(buffer, 0, 4);
            byte[] buffer2 = new byte[this.mSize];
            in.read(buffer2);
            readTrackData(buffer2);
            return;
        }
        System.err.println("Track identifier did not match MTrk!");
    }

    private void readTrackData(byte[] data) throws IOException {
        InputStream in = new ByteArrayInputStream(data);
        long totalTicks = 0;
        while (in.available() > 0) {
            VariableLengthInt delta = new VariableLengthInt(in);
            long totalTicks2 = totalTicks + ((long) delta.getValue());
            MidiEvent E = MidiEvent.parseEvent(totalTicks2, (long) delta.getValue(), in);
            if (E == null) {
                System.out.println("Event skipped!");
            } else if (E.getClass().equals(EndOfTrack.class)) {
                this.mEndOfTrackDelta = E.getDelta();
                E = totalTicks2;
                return;
            } else {
                this.mEvents.add(E);
            }
            totalTicks = totalTicks2;
        }
    }

    public TreeSet<MidiEvent> getEvents() {
        return this.mEvents;
    }

    public int getEventCount() {
        return this.mEvents.size();
    }

    public int getSize() {
        if (this.mSizeNeedsRecalculating) {
            recalculateSize();
        }
        return this.mSize;
    }

    public long getLengthInTicks() {
        if (this.mEvents.size() == 0) {
            return 0;
        }
        return ((MidiEvent) this.mEvents.last()).getTick();
    }

    public long getEndOfTrackDelta() {
        return this.mEndOfTrackDelta;
    }

    public void setEndOfTrackDelta(long delta) {
        this.mEndOfTrackDelta = delta;
    }

    public void insertNote(int channel, int pitch, int velocity, long tick, long duration) {
        int i = channel;
        int i2 = pitch;
        insertEvent(new NoteOn(tick, i, i2, velocity));
        insertEvent(new NoteOff(tick + duration, i, i2, 0));
    }

    public void insertEvent(MidiEvent newEvent) {
        if (newEvent != null) {
            if (this.mClosed) {
                System.err.println("Error: Cannot add an event to a closed track.");
                return;
            }
            MidiEvent prev = null;
            MidiEvent next = null;
            try {
                Class treeSet = Class.forName("java.util.TreeSet");
                Method floor = treeSet.getMethod("floor", new Class[]{Object.class});
                prev = (MidiEvent) floor.invoke(this.mEvents, new Object[]{newEvent});
                next = (MidiEvent) treeSet.getMethod("ceiling", new Class[]{Object.class}).invoke(this.mEvents, new Object[]{newEvent});
            } catch (Exception e) {
                Iterator<MidiEvent> it = this.mEvents.iterator();
                while (it.hasNext()) {
                    next = (MidiEvent) it.next();
                    if (next.getTick() > newEvent.getTick()) {
                        break;
                    }
                    prev = next;
                    next = null;
                }
            }
            this.mEvents.add(newEvent);
            this.mSizeNeedsRecalculating = true;
            if (prev != null) {
                newEvent.setDelta(newEvent.getTick() - prev.getTick());
            } else {
                newEvent.setDelta(newEvent.getTick());
            }
            if (next != null) {
                next.setDelta(next.getTick() - newEvent.getTick());
            }
            this.mSize += newEvent.getSize();
            if (newEvent.getClass().equals(EndOfTrack.class)) {
                if (next != null) {
                    throw new IllegalArgumentException("Attempting to insert EndOfTrack before an existing event. Use closeTrack() when finished with MidiTrack.");
                }
                this.mClosed = true;
            }
        }
    }

    public boolean removeEvent(MidiEvent E) {
        Iterator<MidiEvent> it = this.mEvents.iterator();
        MidiEvent prev = null;
        MidiEvent curr = null;
        MidiEvent next = null;
        while (it.hasNext()) {
            next = (MidiEvent) it.next();
            if (E.equals(curr)) {
                break;
            }
            prev = curr;
            curr = next;
            next = null;
        }
        if (next == null) {
            return this.mEvents.remove(curr);
        }
        if (!this.mEvents.remove(curr)) {
            return false;
        }
        if (prev != null) {
            next.setDelta(next.getTick() - prev.getTick());
        } else {
            next.setDelta(next.getTick());
        }
        return true;
    }

    public void closeTrack() {
        long lastTick = 0;
        if (this.mEvents.size() > 0) {
            lastTick = ((MidiEvent) this.mEvents.last()).getTick();
        }
        insertEvent(new EndOfTrack(lastTick + this.mEndOfTrackDelta, 0));
    }

    public void dumpEvents() {
        Iterator<MidiEvent> it = this.mEvents.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    private void recalculateSize() {
        this.mSize = 0;
        Iterator<MidiEvent> it = this.mEvents.iterator();
        MidiEvent last = null;
        while (it.hasNext()) {
            MidiEvent E = (MidiEvent) it.next();
            this.mSize += E.getSize();
            if (!(last == null || E.requiresStatusByte(last))) {
                this.mSize--;
            }
            last = E;
        }
        this.mSizeNeedsRecalculating = false;
    }

    public void writeToFile(OutputStream out) throws IOException {
        if (!this.mClosed) {
            closeTrack();
        }
        if (this.mSizeNeedsRecalculating) {
            recalculateSize();
        }
        out.write(IDENTIFIER);
        out.write(MidiUtil.intToBytes(this.mSize, 4));
        Iterator<MidiEvent> it = this.mEvents.iterator();
        MidiEvent lastEvent = null;
        while (it.hasNext()) {
            MidiEvent event = (MidiEvent) it.next();
            event.writeToFile(out, event.requiresStatusByte(lastEvent));
            lastEvent = event;
        }
    }
}
