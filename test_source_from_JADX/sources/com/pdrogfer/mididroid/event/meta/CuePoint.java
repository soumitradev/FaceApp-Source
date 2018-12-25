package com.pdrogfer.mididroid.event.meta;

public class CuePoint extends TextualMetaEvent {
    public CuePoint(long tick, long delta, String marker) {
        super(tick, delta, 7, marker);
    }

    public void setCue(String name) {
        setText(name);
    }

    public String getCue() {
        return getText();
    }
}
