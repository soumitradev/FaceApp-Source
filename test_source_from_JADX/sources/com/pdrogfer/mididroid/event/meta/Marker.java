package com.pdrogfer.mididroid.event.meta;

public class Marker extends TextualMetaEvent {
    public Marker(long tick, long delta, String marker) {
        super(tick, delta, 6, marker);
    }

    public void setMarkerName(String name) {
        setText(name);
    }

    public String getMarkerName() {
        return getText();
    }
}
