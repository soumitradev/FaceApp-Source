package com.pdrogfer.mididroid.event.meta;

import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

public abstract class TextualMetaEvent extends MetaEvent {
    protected String mText;

    protected TextualMetaEvent(long tick, long delta, int type, String text) {
        super(tick, delta, type, new VariableLengthInt(text.length()));
        setText(text);
    }

    protected void setText(String t) {
        this.mText = t;
        this.mLength.setValue(t.getBytes().length);
    }

    protected String getText() {
        return this.mText;
    }

    protected int getEventSize() {
        return (this.mLength.getByteCount() + 2) + this.mLength.getValue();
    }

    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(this.mLength.getBytes());
        out.write(this.mText.getBytes());
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
        } else if (!(other instanceof TextualMetaEvent)) {
            return 1;
        } else {
            return this.mText.compareTo(((TextualMetaEvent) other).mText);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(": ");
        stringBuilder.append(this.mText);
        return stringBuilder.toString();
    }
}
