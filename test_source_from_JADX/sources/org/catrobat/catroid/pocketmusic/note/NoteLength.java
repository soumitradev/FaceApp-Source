package org.catrobat.catroid.pocketmusic.note;

import android.util.SparseArray;

public enum NoteLength {
    WHOLE_DOT(6.0f),
    WHOLE(4.0f),
    HALF_DOT(3.0f),
    HALF(2.0f),
    QUARTER_DOT(1.5f),
    QUARTER(1.0f),
    EIGHT_DOT(0.75f),
    EIGHT(0.5f),
    SIXTEENTH(0.25f);
    
    private static final long DEFAULT_TICK_DURATION_MODIFIER = 8;
    private static final int MINUTE_IN_SECONDS = 60;
    private static final int SECOND_IN_MILLISECONDS = 1000;
    private static final NoteLength SMALLEST_NOTE_LENGTH = null;
    private static final NoteLength[] SORTED_NOTE_LENGTHS = null;
    private static SparseArray<long[]> millisecondsCalculationMap;
    private float length;

    static {
        SORTED_NOTE_LENGTHS = new NoteLength[]{SIXTEENTH, EIGHT, EIGHT_DOT, QUARTER, QUARTER_DOT, HALF, HALF_DOT, WHOLE, WHOLE_DOT};
        SMALLEST_NOTE_LENGTH = SIXTEENTH;
        millisecondsCalculationMap = new SparseArray();
    }

    private NoteLength(float length) {
        this.length = length;
    }

    public static NoteLength getNoteLengthFromTickDuration(long duration, int beatsPerMinute) {
        NoteLength noteLength = SMALLEST_NOTE_LENGTH;
        NoteLength[] allNoteLengths = values();
        for (int i = allNoteLengths.length - 1; i >= 0; i--) {
            if (duration - allNoteLengths[i].toTicks(beatsPerMinute) < 0) {
                break;
            }
            noteLength = allNoteLengths[i];
        }
        return noteLength;
    }

    public static NoteLength getNoteLengthFromMilliseconds(long millis, int beatsPerMinute) {
        long[] calculatedMilliseconds = getMilliseconds(beatsPerMinute);
        int i = 0;
        long bottomLimit = calculatedMilliseconds[0];
        int bottomIndex = 0;
        long topLimit = calculatedMilliseconds[calculatedMilliseconds.length - 1];
        int topIndex = 0;
        while (i < calculatedMilliseconds.length) {
            long calculatedMillis = calculatedMilliseconds[i];
            if (millis <= calculatedMillis) {
                topLimit = calculatedMillis;
                topIndex = i;
                break;
            }
            bottomLimit = calculatedMillis;
            bottomIndex = i;
            i++;
        }
        if (Math.abs(bottomLimit - millis) > Math.abs(topLimit - millis)) {
            return SORTED_NOTE_LENGTHS[topIndex];
        }
        return SORTED_NOTE_LENGTHS[bottomIndex];
    }

    public long toTicks(int beatsPerMinute) {
        return (long) Math.round(((float) (((long) beatsPerMinute) * 8)) * this.length);
    }

    public long toMilliseconds(int beatsPerMinute) {
        return (long) Math.round(((((float) beatsPerMinute) * this.length) * 1000.0f) / 60.0f);
    }

    public static long tickToMilliseconds(long tick) {
        return ((1000 * tick) / 60) / 8;
    }

    public boolean hasStem() {
        return (this == WHOLE || this == WHOLE_DOT) ? false : true;
    }

    public NoteFlag getFlag() {
        if (this == SIXTEENTH) {
            return NoteFlag.DOUBLE_FLAG;
        }
        if (this != EIGHT) {
            if (this != EIGHT_DOT) {
                return NoteFlag.NO_FLAG;
            }
        }
        return NoteFlag.SINGLE_FLAG;
    }

    public boolean hasDot() {
        if (!(WHOLE_DOT == this || HALF_DOT == this || QUARTER_DOT == this)) {
            if (EIGHT_DOT != this) {
                return false;
            }
        }
        return true;
    }

    public boolean isHalfOrHigher() {
        if (!(WHOLE_DOT == this || WHOLE == this || HALF_DOT == this)) {
            if (HALF != this) {
                return false;
            }
        }
        return true;
    }

    private static long[] getMilliseconds(int beatsPerMinute) {
        if (millisecondsCalculationMap.get(beatsPerMinute) == null) {
            millisecondsCalculationMap.put(beatsPerMinute, calculateMilliseconds(beatsPerMinute));
        }
        return (long[]) millisecondsCalculationMap.get(beatsPerMinute);
    }

    private static long[] calculateMilliseconds(int beatsPerMinute) {
        long[] milliseconds = new long[SORTED_NOTE_LENGTHS.length];
        for (int i = 0; i < milliseconds.length; i++) {
            milliseconds[i] = SORTED_NOTE_LENGTHS[i].toMilliseconds(beatsPerMinute);
        }
        return milliseconds;
    }
}
