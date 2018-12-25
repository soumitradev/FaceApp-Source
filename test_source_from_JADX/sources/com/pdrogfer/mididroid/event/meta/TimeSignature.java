package com.pdrogfer.mididroid.event.meta;

import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;
import name.antonsmirnov.firmata.FormatHelper;

public class TimeSignature extends MetaEvent {
    public static final int DEFAULT_DIVISION = 8;
    public static final int DEFAULT_METER = 24;
    public static final int METER_EIGHTH = 12;
    public static final int METER_HALF = 48;
    public static final int METER_QUARTER = 24;
    public static final int METER_WHOLE = 96;
    private int mDenominator;
    private int mDivision;
    private int mMeter;
    private int mNumerator;

    public TimeSignature() {
        this(0, 0, 4, 4, 24, 8);
    }

    public TimeSignature(long tick, long delta, int num, int den, int meter, int div) {
        super(tick, delta, 88, new VariableLengthInt(4));
        setTimeSignature(num, den, meter, div);
    }

    public void setTimeSignature(int num, int den, int meter, int div) {
        this.mNumerator = num;
        this.mDenominator = log2(den);
        this.mMeter = meter;
        this.mDivision = div;
    }

    public int getNumerator() {
        return this.mNumerator;
    }

    public int getDenominatorValue() {
        return this.mDenominator;
    }

    public int getRealDenominator() {
        return (int) Math.pow(2.0d, (double) this.mDenominator);
    }

    public int getMeter() {
        return this.mMeter;
    }

    public int getDivision() {
        return this.mDivision;
    }

    protected int getEventSize() {
        return 7;
    }

    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(4);
        out.write(this.mNumerator);
        out.write(this.mDenominator);
        out.write(this.mMeter);
        out.write(this.mDivision);
    }

    public static MetaEvent parseTimeSignature(long tick, long delta, MetaEventData info) {
        MetaEventData metaEventData = info;
        if (metaEventData.length.getValue() != 4) {
            return new GenericMetaEvent(tick, delta, metaEventData);
        }
        int num = metaEventData.data[0];
        int den = metaEventData.data[1];
        return new TimeSignature(tick, delta, num, (int) Math.pow(2.0d, (double) den), metaEventData.data[2], metaEventData.data[3]);
    }

    private int log2(int den) {
        if (den == 2) {
            return 1;
        }
        if (den == 4) {
            return 2;
        }
        if (den == 8) {
            return 3;
        }
        if (den == 16) {
            return 4;
        }
        if (den != 32) {
            return 0;
        }
        return 5;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(this.mNumerator);
        stringBuilder.append("/");
        stringBuilder.append(getRealDenominator());
        return stringBuilder.toString();
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
        } else if (!(other instanceof TimeSignature)) {
            return 1;
        } else {
            TimeSignature o = (TimeSignature) other;
            if (this.mNumerator != o.mNumerator) {
                if (this.mNumerator >= o.mNumerator) {
                    i = 1;
                }
                return i;
            } else if (this.mDenominator == o.mDenominator) {
                return 0;
            } else {
                if (this.mDenominator >= o.mDenominator) {
                    i = 1;
                }
                return i;
            }
        }
    }
}
