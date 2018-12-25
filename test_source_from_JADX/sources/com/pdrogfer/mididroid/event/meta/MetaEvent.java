package com.pdrogfer.mididroid.event.meta;

import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class MetaEvent extends MidiEvent {
    public static final int COPYRIGHT_NOTICE = 2;
    public static final int CUE_POINT = 7;
    public static final int END_OF_TRACK = 47;
    public static final int INSTRUMENT_NAME = 4;
    public static final int KEY_SIGNATURE = 89;
    public static final int LYRICS = 5;
    public static final int MARKER = 6;
    public static final int MIDI_CHANNEL_PREFIX = 32;
    public static final int SEQUENCER_SPECIFIC = 127;
    public static final int SEQUENCE_NUMBER = 0;
    public static final int SMPTE_OFFSET = 84;
    public static final int TEMPO = 81;
    public static final int TEXT_EVENT = 1;
    public static final int TIME_SIGNATURE = 88;
    public static final int TRACK_NAME = 3;
    protected VariableLengthInt mLength;
    protected int mType;

    protected static class MetaEventData {
        public final byte[] data = new byte[this.length.getValue()];
        public final VariableLengthInt length;
        public final int type;

        public MetaEventData(InputStream in) throws IOException {
            this.type = in.read();
            this.length = new VariableLengthInt(in);
            if (this.length.getValue() > 0) {
                in.read(this.data);
            }
        }
    }

    protected abstract int getEventSize();

    protected MetaEvent(long tick, long delta, int type, VariableLengthInt length) {
        super(tick, delta);
        this.mType = type & 255;
        this.mLength = length;
    }

    public void writeToFile(OutputStream out, boolean writeType) throws IOException {
        writeToFile(out);
    }

    protected void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out, true);
        out.write(255);
        out.write(this.mType);
    }

    public static MetaEvent parseMetaEvent(long tick, long delta, InputStream in) throws IOException {
        long j = tick;
        long j2 = delta;
        MetaEventData eventData = new MetaEventData(in);
        boolean isText = false;
        int i = eventData.type;
        if (!(i == 0 || i == 32 || i == 47 || i == 81 || i == 84)) {
            switch (i) {
                case 88:
                case 89:
                    break;
                default:
                    isText = true;
                    break;
            }
        }
        int i2;
        if (isText) {
            String text = new String(eventData.data);
            i2 = eventData.type;
            if (i2 != SEQUENCER_SPECIFIC) {
                switch (i2) {
                    case 1:
                        return new Text(j, j2, text);
                    case 2:
                        return new CopyrightNotice(j, j2, text);
                    case 3:
                        return new TrackName(j, j2, text);
                    case 4:
                        return new InstrumentName(j, j2, text);
                    case 5:
                        return new Lyrics(j, j2, text);
                    case 6:
                        return new Marker(j, j2, text);
                    case 7:
                        return new CuePoint(j, j2, text);
                    default:
                        return new GenericMetaEvent(j, j2, eventData);
                }
            }
            return new SequencerSpecificEvent(j, j2, eventData.data);
        }
        MetaEventData eventData2 = eventData;
        i2 = eventData2.type;
        if (i2 == 0) {
            return SequenceNumber.parseSequenceNumber(j, j2, eventData2);
        }
        if (i2 == 32) {
            return MidiChannelPrefix.parseMidiChannelPrefix(j, j2, eventData2);
        }
        if (i2 == 47) {
            return new EndOfTrack(j, j2);
        }
        if (i2 == 81) {
            return Tempo.parseTempo(j, j2, eventData2);
        }
        if (i2 == 84) {
            return SmpteOffset.parseSmpteOffset(j, j2, eventData2);
        }
        switch (i2) {
            case 88:
                return TimeSignature.parseTimeSignature(j, j2, eventData2);
            case 89:
                return KeySignature.parseKeySignature(j, j2, eventData2);
            default:
                System.out.println("Completely broken in MetaEvent.parseMetaEvent()");
                return null;
        }
    }
}
