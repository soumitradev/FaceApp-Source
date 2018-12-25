package com.pdrogfer.mididroid.event.meta;

public class CopyrightNotice extends TextualMetaEvent {
    public CopyrightNotice(long tick, long delta, String text) {
        super(tick, delta, 2, text);
    }

    public void setNotice(String t) {
        setText(t);
    }

    public String getNotice() {
        return getText();
    }
}
